package com.baesiru.order.domain.service;

import com.baesiru.order.domain.repository.OrderItem;
import com.baesiru.order.domain.repository.OrderItemRepository;
import com.baesiru.order.domain.service.model.product.MessageUpdateRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> findByOrderId(Long id) {
        return orderItemRepository.findByOrderId(id);
    }

    public void publishUpdateProduct(MessageUpdateRequest messageUpdateRequest) {
        rabbitTemplate.convertAndSend("product.topic.exchange", "product.update", messageUpdateRequest);
    }

    public void publishCancelProduct(MessageUpdateRequest messageUpdateRequest) {
        rabbitTemplate.convertAndSend("product.topic.exchange", "product.cancel", messageUpdateRequest);
    }
}
