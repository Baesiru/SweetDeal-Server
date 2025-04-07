package com.baesiru.store.domain.store.controller.model.request;

import com.baesiru.global.annotation.ValidEnum;
import com.baesiru.store.domain.store.repository.enums.StoreCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Size(min = 1, max = 50, message = "이름은 1자 이상, 50자 이하로 입력해주세요.")
    @NotBlank(message = "필수 입력 사항입니다.")
    private String name;
    @Size(min = 1, max = 200, message = "주소는 1자 이상, 200자 이하로 입력해주세요.")
    @NotBlank(message = "필수 입력 사항입니다.")
    private String address;
    @Pattern(
            regexp = "^(01[016789]-\\d{3,4}-\\d{4}|0[2-9][0-9]?-\\d{3,4}-\\d{4})$",
            message = "올바른 전화번호 형식을 입력하세요 (예: 010-1234-5678, 02-1234-5678)"
    )
    @NotBlank(message = "필수 입력 사항입니다.")
    private String phone;
    @Pattern(
            regexp = "^\\d{3}-\\d{2}-\\d{5}$",
            message = "올바른 사업자등록번호 형식을 입력하세요 (예: 123-45-67890)"
    )
    @NotBlank(message = "필수 입력 사항입니다.")
    private String businessNumber;
    @ValidEnum(enumClass = StoreCategory.class)
    private StoreCategory category;
    @NotBlank(message = "필수 입력 사항입니다.")
    private String latitude;
    @NotBlank(message = "필수 입력 사항입니다.")
    private String longitude;
    private List<String> serverNames;
}