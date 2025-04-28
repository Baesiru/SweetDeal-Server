package com.baesiru.order.domain.controller.model.response;

import com.baesiru.order.domain.repository.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long totalPrice;
    private Long userId;
    private Long storeId;
    private OrderStatus status;
    private LocalDateTime orderedAt;
    private LocalDateTime canceledAt;
    private List<OrderItemResponse> orderItemResponses;
}
