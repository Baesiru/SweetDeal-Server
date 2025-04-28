package com.baesiru.order.domain.service;

import com.baesiru.order.domain.service.model.product.ProductInternalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sweetdeal-products", path = "/internal")
public interface ProductFeign {
    @GetMapping(value = "/product/{id}", headers = "X-Internal=true")
    ResponseEntity<ProductInternalResponse> getProduct(@PathVariable Long id);
}
