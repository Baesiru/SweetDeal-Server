package com.baesiru.store.domain.store.repository.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreStatus {
    REGISTERED("등록"),
    UNREGISTERED("탈퇴"),
    PENDING("대기"),
    REJECTED("거절")
    ;
    private final String description;
}
