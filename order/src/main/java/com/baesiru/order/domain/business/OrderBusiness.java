package com.baesiru.order.domain.business;

import com.baesiru.global.annotation.Business;
import com.baesiru.global.resolver.AuthUser;
import com.baesiru.order.common.errorcode.OrderErrorCode;
import com.baesiru.order.common.exception.order.*;
import com.baesiru.order.common.response.MessageResponse;
import com.baesiru.order.domain.controller.model.request.CancelRequest;
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
import com.baesiru.order.domain.service.model.product.ProductInform;
import com.baesiru.order.domain.service.model.product.ProductInternalRequest;
import com.baesiru.order.domain.service.model.product.ProductInternalResponse;
import com.baesiru.order.domain.service.model.store.StoreSimpleResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.hibernate.query.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        List<Long> productIds = orderItemRequests.stream()
                .map(OrderItemRequest::getProductId)
                .toList();
        ProductInternalRequest productInternalRequest = new ProductInternalRequest(productIds);
        ResponseEntity<ProductInternalResponse> response = productFeign.getProduct(productInternalRequest);

        ProductInternalResponse productInternalResponse = response.getBody();
        if (productInternalResponse == null) {
            return false;
        }

        Map<Long, ProductInform> productInformMap = productInternalResponse.getProductInforms().stream()
                .collect(Collectors.toMap(ProductInform::getId, Function.identity()));

        for (OrderItemRequest orderItemRequest : orderItemRequests) {
            ProductInform productInform = productInformMap.get(orderItemRequest.getProductId());
            if (productInform.getCount() < orderItemRequest.getCount()) {
                return false;
            }
            if (productInform.getExpiredAt().isBefore(LocalDateTime.now())) {
                return false;
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setCount(orderItemRequest.getCount());
            orderItem.setProductId(orderItemRequest.getProductId());
            orderItem.setPrice(productInform.getDiscountedPrice());
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
            throw new UserNotEqualException(OrderErrorCode.USER_NOT_EQUAL);
        List<OrderItem> orderItems = orderItemService.findByOrderId(orders.getId());

        List<Long> productIds = orderItems.stream()
                .map(OrderItem::getProductId)
                .toList();
        ProductInternalRequest productInternalRequest = new ProductInternalRequest(productIds);
        ResponseEntity<ProductInternalResponse> response = productFeign.getProduct(productInternalRequest);
        ProductInternalResponse productInternalResponse = response.getBody();

        if (productInternalResponse == null) {
            throw new ProductNotExistException(OrderErrorCode.PRODUCT_NOT_EXIST);
        }

        Map<Long, ProductInform> productInformMap = productInternalResponse.getProductInforms().stream()
                .collect(Collectors.toMap(ProductInform::getId, Function.identity()));

        for (OrderItem orderItem : orderItems) {
            ProductInform productInform = productInformMap.get(orderItem.getProductId());
            if (productInform.getCount() < orderItem.getCount()) {
                throw new ProductCountNotEnoughException(OrderErrorCode.PRODUCT_COUNT_NOT_ENOUGH);
            }
            if (productInform.getExpiredAt().isBefore(LocalDateTime.now())) {
                throw new ExpiredException(OrderErrorCode.EXPIRED_ERROR);
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
            throw new StoreNotExistException(OrderErrorCode.STORE_NOT_EXIST);
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

    public MessageResponse cancelOrder(CancelRequest cancelRequest, AuthUser authUser) {
        Orders order = orderService.findFirstByIdAndStatusOrderByIdDesc(cancelRequest.getId());
        if (order.getUserId() != Long.parseLong(authUser.getUserId()))
            throw new UserNotEqualException(OrderErrorCode.USER_NOT_EQUAL);
        List<OrderItem> orderItems = orderItemService.findByOrderId(order.getId());
        //List<MessageUpdateRequest> messageUpdateRequests = orderItems.stream()
        //        .map(orderItem -> modelMapper.map(orderItem, MessageUpdateRequest.class))
        //        .toList();
        for (OrderItem orderItem : orderItems) {
            MessageUpdateRequest messageUpdateRequest = MessageUpdateRequest.builder()
                    .id(orderItem.getProductId())
                    .count(orderItem.getCount())
                    .build();
            orderItemService.publishCancelProduct(messageUpdateRequest);
        }
        order.setStatus(OrderStatus.CANCELED);
        orderService.save(order);
        MessageResponse messageResponse = new MessageResponse("주문이 취소되었습니다.");
        return messageResponse;
    }

    public MessageResponse cancelStoreOrder(CancelRequest cancelRequest, AuthUser authUser) {
        try {
            ResponseEntity<StoreSimpleResponse> response = storeFeign.getStore(authUser.getUserId());
            StoreSimpleResponse storeSimpleResponse = response.getBody();
            Orders order = orderService.findFirstByIdAndStatusOrderByIdDesc(cancelRequest.getId());
            if (order.getStoreId() != storeSimpleResponse.getId()) {
                throw new StoreNotExistException(OrderErrorCode.STORE_NOT_EXIST);
            }
            order.setStatus(OrderStatus.CANCELED);
            orderService.save(order);
            MessageResponse messageResponse = new MessageResponse("주문이 취소되었습니다.");
            return messageResponse;
        } catch (FeignException e) {
            throw new StoreNotExistException(OrderErrorCode.STORE_NOT_EXIST);
        }
    }
}
