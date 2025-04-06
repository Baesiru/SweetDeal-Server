package com.baesiru.store.domain.store.infra.client;

import com.baesiru.global.api.Api;
import com.baesiru.store.domain.store.infra.client.model.RoleRequest;
import com.baesiru.store.domain.store.infra.client.model.UserRole;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sweetdeal-users")
public interface UserClient {

    @PostMapping("/users/role")
    @Headers("X-Internal-Call: true")
    Api<?> changeRole(@RequestBody RoleRequest RoleRequest);
}
