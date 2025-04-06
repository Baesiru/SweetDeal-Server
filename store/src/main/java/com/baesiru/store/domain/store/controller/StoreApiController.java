package com.baesiru.store.domain.store.controller;

import com.baesiru.global.annotation.AuthenticatedUser;
import com.baesiru.global.api.Api;
import com.baesiru.global.resolver.AuthUser;
import com.baesiru.store.common.response.MessageResponse;
import com.baesiru.store.domain.store.business.StoreBusiness;
import com.baesiru.store.domain.store.controller.model.request.RegisterRequest;
import com.baesiru.store.domain.store.controller.model.response.StoreResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreApiController {
    @Autowired
    private StoreBusiness storeBusiness;

    @PostMapping("/store")
    public Api<MessageResponse> register(@RequestBody @Valid RegisterRequest registerRequest,
                           @AuthenticatedUser AuthUser authUser){
        MessageResponse response = storeBusiness.register(registerRequest, authUser);
        return Api.OK(response);
    }

    @GetMapping("/store/owner")
    public Api<StoreResponse> getOwnStore(@AuthenticatedUser AuthUser authUser){
        StoreResponse storeResponse = storeBusiness.getOwnStore(authUser);
        return Api.OK(storeResponse);
    }
}
