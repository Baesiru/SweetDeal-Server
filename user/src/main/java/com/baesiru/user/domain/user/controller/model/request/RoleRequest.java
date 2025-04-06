package com.baesiru.user.domain.user.controller.model.request;

import com.baesiru.user.domain.user.repository.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    private String userId;
    private UserRole role;
}
