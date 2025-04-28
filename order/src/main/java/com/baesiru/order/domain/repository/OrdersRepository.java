package com.baesiru.order.domain.repository;


import com.baesiru.order.domain.repository.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findFirstByIdAndStatusOrderByIdDesc(Long id, OrderStatus orderStatus);
}
