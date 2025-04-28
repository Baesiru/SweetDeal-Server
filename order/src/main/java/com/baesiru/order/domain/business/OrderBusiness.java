package com.baesiru.order.domain.business;

import com.baesiru.global.annotation.Business;
import com.baesiru.global.resolver.AuthUser;
import com.baesiru.order.common.response.MessageResponse;
import com.baesiru.order.domain.controller.model.request.OrderItemRequest;
import com.baesiru.order.domain.controller.model.request.OrderCreateRequest;
import com.baesiru.order.domain.controller.model.request.PaymentRequest;
import com.baesiru.order.domain.controller.model.response.OrderCreateResponse;
import com.baesiru.order.domain.controller.model.response.OrderItemResponse;
import com.baesiru.order.domain.controller.model.response.OrderResponse;
import com.baesiru.order.domain.repository.Orders;
import com.baesiru.order.domain.repository.OrderItem;
import com.baesiru.order.domain.repository.enums.OrderStatus;
import com.baesiru.order.domain.service.OrderItemService;
import com.baesiru.order.domain.service.OrderService;
import com.baesiru.order.domain.service.ProductFeign;
import com.baesiru.order.domain.service.StoreFeign;
import com.baesiru.order.domain.service.model.product.MessageUpdateRequest;
import com.baesiru.order.domain.service.model.product.ProductInternalResponse;
import com.baesiru.order.domain.service.model.store.StoreSimpleResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Business
@Slf4j
public class OrderBusiness {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductFeign productFeign;
    @Autowired
    private StoreFeign storeFeign;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public OrderCreateResponse createOrder(OrderCreateRequest orderCreateRequest, AuthUser authUser) {
        Orders order = Orders.builder()
                .storeId(orderCreateRequest.getStoreId())
                .userId(Long.parseLong(authUser.getUserId()))
                .status(OrderStatus.ORDERED)
                .orderedAt(LocalDateTime.now())
                .totalPrice(0L)
                .build();
        order = orderService.save(order);
        List<OrderItemRequest> orderItemRequests = orderCreateRequest.getOrderItemRequests();
        boolean isValid = createOrderItems(orderItemRequests, order);
        if (!isValid) {
            throw new IllegalArgumentException("주문이 취소되었습니다.");
        }
        return modelMapper.map(order, OrderCreateResponse.class);
    }

    public boolean createOrderItems(List<OrderItemRequest> orderItemRequests, Orders order) {
        for (OrderItemRequest orderItemRequest : orderItemRequests) {
            ResponseEntity<ProductInternalResponse> response = productFeign.getProduct(orderItemRequest.getProductId());
            ProductInternalResponse internalResponse = response.getBody();
            if (internalResponse == null) {
                return false;
            }
            if (internalResponse.getCount() < orderItemRequest.getCount()) {
                return false;
            }
            if (internalResponse.getExpiredAt().isBefore(LocalDateTime.now())) {
                return false;
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setCount(orderItemRequest.getCount());
            orderItem.setProductId(orderItemRequest.getProductId());
            orderItem.setPrice(internalResponse.getDiscountedPrice());
            orderItem.setTotalPrice(orderItem.getCount() * orderItem.getPrice());
            order.setTotalPrice(order.getTotalPrice() + orderItem.getTotalPrice());
            orderItemService.save(orderItem);
        }
        return true;
    }

    public MessageResponse payment(PaymentRequest paymentRequest, AuthUser authUser) {
        //결제는 성공했다고 가정
        Orders orders = orderService.findFirstByIdAndStatusOrderByIdDesc(paymentRequest.getOrderId());
        if (orders.getUserId() != Long.parseLong(authUser.getUserId()))
            throw new IllegalArgumentException("유저 정보가 다릅니다.");
        List<OrderItem> orderItems = orderItemService.findByOrderId(orders.getId());
        for (OrderItem orderItem : orderItems) {
            //OrderItem들의 재고 확인
            ResponseEntity<ProductInternalResponse> response = productFeign.getProduct(orderItem.getProductId());
            ProductInternalResponse internalResponse = response.getBody();
            if (internalResponse == null) {
                throw new IllegalArgumentException("상품이 존재하지 않습니다.");
            }
            if (internalResponse.getCount() < orderItem.getCount()) {
                throw new IllegalArgumentException("상품의 개수가 모자랍니다.");
            }
            if (internalResponse.getExpiredAt().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("유효기간이 지났습니다.");
            }
            //OrderItem들의 재고 감소(메시지큐 + 비관적락)
            MessageUpdateRequest messageUpdateRequest = MessageUpdateRequest.builder()
                    .id(orderItem.getProductId())
                    .count(orderItem.getCount())
                    .build();
            orderItemService.publishUpdateProduct(messageUpdateRequest);
        }
        //Orders의 상태 COMPLETED로 변경
        orders.setStatus(OrderStatus.COMPLETED);
        orderService.save(orders);
        MessageResponse messageResponse = new MessageResponse("결제가 완료되었습니다.");
        return messageResponse;
    }

    public List<OrderResponse> getOrder(AuthUser authUser) {
        List<Orders> orders = orderService.findByUserId(Long.parseLong(authUser.getUserId()));
        List<OrderResponse> orderResponses = getOrderResponses(orders);
        return orderResponses;

    }

    public List<OrderResponse> getStoreOrder(AuthUser authUser) {
        try {
            ResponseEntity<StoreSimpleResponse> response = storeFeign.getStore(authUser.getUserId());
            StoreSimpleResponse storeSimpleResponse = response.getBody();
            List<Orders> orders = orderService.findByStoreId(storeSimpleResponse.getId());
            List<OrderResponse> orderResponses = getOrderResponses(orders);
            return orderResponses;

        } catch (FeignException e) {
            throw new IllegalArgumentException("가게가 존재하지 않습니다.");
        }
    }

    public List<OrderResponse> getOrderResponses(List<Orders> orders) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Orders order : orders) {
            OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
            List<OrderItem> orderItems = orderItemService.findByOrderId(order.getId());
            orderResponse.setOrderItemResponses(orderItems.stream().map(orderItem -> modelMapper.map(orderItem, OrderItemResponse.class)).toList());
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }
}
