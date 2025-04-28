package com.baesiru.order.domain.service;

import com.baesiru.order.domain.service.model.store.StoreSimpleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sweetdeal-stores", path = "/internal")
public interface StoreFeign {
    @GetMapping(value = "/store/{userId}", headers = "X-Internal=true")
    ResponseEntity<StoreSimpleResponse> getStore(@PathVariable String userId);
}
