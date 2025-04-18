package com.baesiru.user.domain.user.controller.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordRequest {
    private String currPassword;
    private String newPassword;
}
