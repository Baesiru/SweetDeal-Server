package com.baesiru.order.domain.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Long count;
    private Long price;
    private Long totalPrice;
    private Long productId;
}
