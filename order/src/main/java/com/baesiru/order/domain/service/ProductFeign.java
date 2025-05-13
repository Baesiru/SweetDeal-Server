package com.baesiru.order.domain.service;

import com.baesiru.order.domain.service.model.product.ProductInternalRequest;
import com.baesiru.order.domain.service.model.product.ProductInternalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sweetdeal-products", path = "/internal")
public interface ProductFeign {
    @PostMapping(value = "/product", headers = "X-Internal=true")
    ResponseEntity<ProductInternalResponse> getProduct(@RequestBody ProductInternalRequest productInternalRequest);
}
