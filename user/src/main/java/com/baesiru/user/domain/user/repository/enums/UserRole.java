package com.baesiru.user.domain.user.repository.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    USER("일반사용자"),
    STORE("점주"),
    ADMIN("관리자")
    ;

    private final String description;
}
