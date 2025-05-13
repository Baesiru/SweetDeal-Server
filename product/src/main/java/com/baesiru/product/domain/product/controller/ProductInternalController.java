package com.baesiru.product.domain.product.controller;

import com.baesiru.product.domain.product.business.ProductBusiness;
import com.baesiru.product.domain.product.controller.model.request.ProductInternalRequest;
import com.baesiru.product.domain.product.controller.model.response.ProductInform;
import com.baesiru.product.domain.product.controller.model.response.ProductInternalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
public class ProductInternalController {
    @Autowired
    private ProductBusiness productBusiness;

    @PostMapping("/product")
    public ResponseEntity<?> getProduct(@RequestBody ProductInternalRequest productInternalRequest) {
        ProductInternalResponse productInternalResponse = productBusiness.getProductInternal(productInternalRequest);
        return ResponseEntity.ok(productInternalResponse);
    }
}
