package com.baesiru.product.domain.product.controller;

import com.baesiru.product.domain.product.business.ProductBusiness;
import com.baesiru.product.domain.product.controller.model.response.ProductInternalResponse;
import com.baesiru.product.domain.product.repository.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class ProductInternalController {
    @Autowired
    private ProductBusiness productBusiness;

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        ProductInternalResponse productInternalResponse = productBusiness.getProductInternal(id);
        return ResponseEntity.ok(productInternalResponse);
    }
}
