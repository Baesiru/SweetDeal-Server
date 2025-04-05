package com.baesiru.user.domain.user.controller;

import com.baesiru.global.annotation.AuthenticatedUser;
import com.baesiru.global.api.Api;
import com.baesiru.global.resolver.AuthUser;
import com.baesiru.user.common.response.MessageResponse;
import com.baesiru.user.domain.user.business.UserBusiness;
import com.baesiru.user.domain.user.controller.model.request.UnregisterRequest;
import com.baesiru.user.domain.user.controller.model.request.UpdatePasswordRequest;
import com.baesiru.user.domain.user.controller.model.response.UserInformationResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {
    @Autowired
    private UserBusiness userBusiness;
    @PostMapping("/unregister")
    public Api<MessageResponse> unRegister(@RequestBody @Valid UnregisterRequest unregisterRequest,
                                   @AuthenticatedUser AuthUser authUser) {
        MessageResponse response = userBusiness.unRegister(unregisterRequest, authUser);
        return Api.OK(response);
    }

    @PostMapping("/updatePassword")
    public Api<MessageResponse> updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest,
                                       @AuthenticatedUser AuthUser authUser) {
        MessageResponse response = userBusiness.updatePassword(updatePasswordRequest, authUser);
        return Api.OK(response);
    }

    @PostMapping("/info")
    public Api<UserInformationResponse> getUserInformation(@AuthenticatedUser AuthUser authUser) {
        UserInformationResponse response = userBusiness.getUserInformation(authUser);
        return Api.OK(response);
    }

}
