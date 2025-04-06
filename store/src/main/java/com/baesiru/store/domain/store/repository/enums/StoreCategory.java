package com.baesiru.store.domain.store.repository.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreCategory {
    CONVENIENCE_STORE("편의점"),
    SUPERMARKET("마트"),
    BAKERY("빵집"),
    ETC("기타")
    ;

    private final String description;

}
