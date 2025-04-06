package com.baesiru.store.domain.store.controller;

import com.baesiru.global.annotation.AuthenticatedUser;
import com.baesiru.global.api.Api;
import com.baesiru.global.resolver.AuthUser;
import com.baesiru.store.common.response.MessageResponse;
import com.baesiru.store.domain.store.business.StoreBusiness;
import com.baesiru.store.domain.store.controller.model.request.LocationRequest;
import com.baesiru.store.domain.store.controller.model.request.RegisterRequest;
import com.baesiru.store.domain.store.controller.model.response.NearbyStoreResponse;
import com.baesiru.store.domain.store.controller.model.response.OwnerStoreResponse;
import com.baesiru.store.domain.store.controller.model.response.UserStoreResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Api<OwnerStoreResponse> getOwnStore(@AuthenticatedUser AuthUser authUser){
        OwnerStoreResponse ownerStoreResponse = storeBusiness.getOwnStore(authUser);
        return Api.OK(ownerStoreResponse);
    }

    @GetMapping("/store/{id}")
    public Api<UserStoreResponse> getUserStore(@PathVariable Long id) {
        UserStoreResponse userStoreResponse = storeBusiness.getUserStore(id);
        return Api.OK(userStoreResponse);
    }

    @PostMapping("/store/nearby")
    public Api<?> getNearbyStore(@RequestBody LocationRequest locationRequest) {
        List<NearbyStoreResponse> response = storeBusiness.getNearbyStore(locationRequest);
        return Api.OK(response);
    }
}
