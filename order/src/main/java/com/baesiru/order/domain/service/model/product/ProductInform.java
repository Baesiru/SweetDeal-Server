package com.baesiru.order.domain.service.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInform {
    private Long id;
    private Long count;
    private Long discountedPrice;
    private ProductStatus status;
    private LocalDateTime expiredAt;
}
