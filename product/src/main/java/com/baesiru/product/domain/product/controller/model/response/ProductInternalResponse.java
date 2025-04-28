package com.baesiru.product.domain.product.controller.model.response;

import com.baesiru.product.domain.product.repository.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInternalResponse {
    private Long id;
    private Long count;
    private Long discountedPrice;
    private ProductStatus status;
    private LocalDateTime expiredAt;
}
