package com.baesiru.order.domain.service;

import com.baesiru.order.domain.controller.model.request.PaymentRequest;
import com.baesiru.order.domain.repository.Orders;
import com.baesiru.order.domain.repository.OrdersRepository;
import com.baesiru.order.domain.repository.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Orders> findByUserId(Long userId) {
        List<Orders> orders = ordersRepository.findByUserId(userId);
        return orders;
    }

    public List<Orders> findByStoreId(Long storeId) {
        List<Orders> orders = ordersRepository.findByStoreId(storeId);
        return orders;
    }
}
