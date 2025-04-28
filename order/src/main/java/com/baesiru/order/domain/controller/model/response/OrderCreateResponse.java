package com.baesiru.order.domain.controller.model.response;

import com.baesiru.order.domain.service.model.product.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateResponse {
    private Long id;
    private ProductStatus status;
}
