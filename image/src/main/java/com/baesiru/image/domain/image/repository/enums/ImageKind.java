package com.baesiru.image.domain.image.repository.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageKind {
    STORE("가게 이미지"),
    PRODUCT("상품 이미지")
    ;
    private final String description;
}
