package com.baesiru.user.domain.user.controller.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Pattern(
            regexp = "^[0-9a-zA-Z]{1,50}@[0-9a-zA-Z]{1,24}+(\\.[0-9a-zA-Z]+){1,24}$",
            message = "올바른 이메일 형식을 입력하세요 (예: example@domain.com)"
    )
    @NotBlank(message = "필수 입력 사항입니다.")
    private String email;
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,100}$",
            message = "대문자, 소문자, 숫자를 포함하고 8자 이상이어야 합니다."
    )
    @NotBlank(message = "필수 입력 사항입니다.")
    private String password;
    @Size(min = 1, max = 50, message = "이름은 1자 이상, 50자 이하로 입력해주세요.")
    @NotBlank(message = "필수 입력 사항입니다.")
    private String name;
    @Pattern(
            regexp = "^(?!.*[\\\\{}()<>$%^&*_=|`]).{2,50}$",
            message = "2자 이상 50자 이하이며, 특정 특수문자는 제외됩니다."
    )
    @NotBlank(message = "필수 입력 사항입니다.")
    private String nickname;
    @Pattern(
            regexp = "^01[016789]-\\d{3,4}-\\d{4}$",
            message = "올바른 전화번호 형식을 입력하세요 (예: 010-1234-5678)"
    )
    @NotBlank(message = "필수 입력 사항입니다.")
    private String phone;
}
