package com.baesiru.product.domain.product.service;

import com.baesiru.product.domain.product.service.model.store.StoreSimpleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "sweetdeal-stores", path = "/internal")
public interface StoreFeign {
    @GetMapping(value = "/store/{userId}", headers = "X-Internal=true")
    ResponseEntity<StoreSimpleResponse> getStore(@PathVariable String userId);
}
