package com.baesiru.product.domain.product.controller.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {
    private String name;
    private Long count;
    private Long originalPrice;
    private Long discountedPrice;
    private String description;
    private LocalDateTime expiredAt;
    private LocalDateTime saleClosedAt;
    private List<String> serverNames;
}
