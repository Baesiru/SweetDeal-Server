package com.baesiru.store.domain.store.service;

import com.baesiru.global.api.Api;
import com.baesiru.store.domain.store.service.model.user.RoleRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sweetdeal-users", path = "/internal")
public interface UserFeign {

    @PostMapping(value = "/users/role", headers = "X-Internal=true")
    Api<?> changeRole(@RequestBody RoleRequest RoleRequest);
}
