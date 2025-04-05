package com.baesiru.user.domain.user.controller.model.response;

import com.baesiru.user.domain.user.repository.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationResponse {
    private String email;
    private String name;
    private String nickname;
    private String phone;
    private UserRole role;
}
