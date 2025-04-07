package com.baesiru.store.domain.store.infra.client;

import com.baesiru.global.api.Api;
import com.baesiru.store.domain.store.infra.client.model.user.RoleRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sweetdeal-users", path = "/internal")
public interface UserClient {

    @PostMapping(value = "/users/role", headers = "X-Internal=true")
    Api<?> changeRole(@RequestBody RoleRequest RoleRequest);
}
