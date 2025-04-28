package com.baesiru.order.domain.service;

import com.baesiru.order.domain.controller.model.request.PaymentRequest;
import com.baesiru.order.domain.repository.Orders;
import com.baesiru.order.domain.repository.OrdersRepository;
import com.baesiru.order.domain.repository.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrdersRepository ordersRepository;

    public Orders save(Orders order) {
        return ordersRepository.save(order);
    }

    public Orders findFirstByIdAndStatusOrderByIdDesc(Long id) {
        Optional<Orders> order = ordersRepository.findFirstByIdAndStatusOrderByIdDesc(id, OrderStatus.ORDERED);
        if (order.isEmpty()) {
            throw new IllegalArgumentException("주문이 존재하지 않습니다.");
        }
        return order.get();
    }
}
