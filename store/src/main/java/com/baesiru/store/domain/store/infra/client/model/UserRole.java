package com.baesiru.store.domain.store.infra.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    USER("일반사용자"),
    OWNER("점주"),
    ADMIN("관리자")
    ;

    private final String description;
}
