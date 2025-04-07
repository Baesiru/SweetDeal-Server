package com.baesiru.user.domain.user.controller;

import com.baesiru.global.api.Api;
import com.baesiru.user.common.response.MessageResponse;
import com.baesiru.user.domain.user.business.UserBusiness;
import com.baesiru.user.domain.user.controller.model.request.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInternalController {
    @Autowired
    private UserBusiness userBusiness;

    @PostMapping("/internal/users/role")
    public Api<?> changeRole(@RequestBody RoleRequest roleRequest) {
        MessageResponse response = userBusiness.chanageRole(roleRequest);
        return Api.OK(response);
    }
}
