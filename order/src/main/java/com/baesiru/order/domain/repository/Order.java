package com.baesiru.order.domain.repository;

import com.baesiru.order.domain.repository.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long totalPrice;
    private Long userId;
    private Long storeId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime orderedAt;
    private LocalDateTime canceledAt;
}
