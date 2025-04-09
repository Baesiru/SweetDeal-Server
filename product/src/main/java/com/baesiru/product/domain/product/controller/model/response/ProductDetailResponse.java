package com.baesiru.product.domain.product.controller.model.response;

import com.baesiru.product.domain.product.repository.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse {
    private Long id;
    private String name;
    private Long count;
    private Long originalPrice;
    private Long discountedPrice;
    private String description;
    private ProductStatus status;
    private Long storeId;
    private String storeName;
    private LocalDateTime expiredAt;
    private LocalDateTime registeredAt;
}
