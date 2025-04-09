package com.baesiru.user.domain.user.controller;

import com.baesiru.global.annotation.AuthenticatedUser;
import com.baesiru.global.api.Api;
import com.baesiru.global.resolver.AuthUser;
import com.baesiru.user.common.response.MessageResponse;
import com.baesiru.user.domain.user.business.UserBusiness;
import com.baesiru.user.domain.user.controller.model.request.RoleRequest;
import com.baesiru.user.domain.user.controller.model.response.UserInformationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class UserInternalController {
    @Autowired
    private UserBusiness userBusiness;

    @PostMapping("/users/role")
    public Api<?> changeRole(@RequestBody RoleRequest roleRequest) {
        MessageResponse response = userBusiness.chanageRole(roleRequest);
        return Api.OK(response);
    }

}
