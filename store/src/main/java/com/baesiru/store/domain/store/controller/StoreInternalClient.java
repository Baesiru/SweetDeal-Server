package com.baesiru.store.domain.store.controller;

import com.baesiru.store.domain.store.business.StoreBusiness;
import com.baesiru.store.domain.store.controller.model.response.StoreProductResponse;
import com.baesiru.store.domain.store.controller.model.response.StoreSimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
public class StoreInternalClient {
    @Autowired
    private StoreBusiness storeBusiness;

    @GetMapping("/store/{userId}")
    public ResponseEntity<StoreSimpleResponse> getStore(@PathVariable String userId) {
        StoreSimpleResponse response = storeBusiness.getSimpleStore(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/store/product/{id}")
    public ResponseEntity<StoreProductResponse> getProductStore(@PathVariable Long id) {
        StoreProductResponse response = storeBusiness.getProductStore(id);
        return ResponseEntity.ok(response);
    }
}
