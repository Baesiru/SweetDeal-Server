package com.baesiru.store.common.errorcode;

import com.baesiru.global.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreErrorCode implements ErrorCode {
    EXISTS_BUSINESS_NUMBER(409, "이미 존재하는 사업자번호입니다."),
    STORE_NOT_FOUND(404, "가게를 찾을 수 없습니다.")
    ;

    private final Integer httpCode;
    private final String description;
}
