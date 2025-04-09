package com.baesiru.product.domain.product.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsResponse {
    private Long storeId;
    private List<ProductDetailResponse> products;
}
