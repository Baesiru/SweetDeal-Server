package com.baesiru.user.domain.user.controller.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuplicationNicknameRequest {
    @Pattern(
            regexp = "^(?!.*[\\\\{}()<>$%^&*_=|`]).{2,50}$",
            message = "2자 이상 50자 이하이며, 특정 특수문자는 제외됩니다."
    )
    @NotBlank(message = "필수 입력 사항입니다.")
    private String nickname;
}
