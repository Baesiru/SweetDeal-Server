package com.baesiru.store.domain.store.controller.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {
    @NotBlank(message = "필수 입력 사항입니다.")
    private BigDecimal latitude;
    @NotBlank(message = "필수 입력 사항입니다.")
    private BigDecimal longitude;
    @NotBlank(message = "필수 입력 사항입니다.")
    private BigDecimal radius;
}
