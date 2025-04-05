package com.baesiru.user.domain.user.controller.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnregisterRequest {
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,100}$",
            message = "대문자, 소문자, 숫자를 포함하고 8자 이상이어야 합니다."
    )
    @NotBlank(message = "필수 입력 사항입니다.")
    private String password;
}
