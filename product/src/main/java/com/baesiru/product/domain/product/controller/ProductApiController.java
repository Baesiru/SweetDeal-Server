package com.baesiru.product.domain.product.controller;

import com.baesiru.global.annotation.AuthenticatedUser;
import com.baesiru.global.api.Api;
import com.baesiru.global.resolver.AuthUser;
import com.baesiru.product.common.response.MessageResponse;
import com.baesiru.product.domain.product.business.ProductBusiness;
import com.baesiru.product.domain.product.controller.model.request.ProductCreateRequest;
import com.baesiru.product.domain.product.controller.model.response.ProductDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductApiController {
    @Autowired
    private ProductBusiness productBusiness;

    @PostMapping("/product")
    public Api<MessageResponse> create(@RequestBody ProductCreateRequest productCreateRequest,
                                       @AuthenticatedUser AuthUser authUser) {
        MessageResponse messageResponse = productBusiness.create(productCreateRequest, authUser);
        return Api.OK(messageResponse);
    }

    @GetMapping("/product/{id}")
    public Api<ProductDetailResponse> getProductDetail(@PathVariable Long id) {
        ProductDetailResponse response = productBusiness.getProductDetail(id);
        return Api.OK(response);
    }

    @PostMapping("/product/{id}/delete")
    public Api<MessageResponse> delete(@PathVariable Long id,
                                       @AuthenticatedUser AuthUser authUser) {
        MessageResponse response = productBusiness.delete(id, authUser);
        return Api.OK(response);
    }


}
