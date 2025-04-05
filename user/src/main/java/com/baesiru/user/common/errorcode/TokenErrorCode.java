package com.baesiru.user.common.errorcode;

import com.baesiru.global.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCode {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(),"유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(),"만료된 토큰입니다."),
    TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED.value(),"알 수 없는 토큰 에러입니다."),
    NOT_PERMITTED(HttpStatus.FORBIDDEN.value(),"허용되지 않은 접근입니다.")
    ;

    private final Integer httpCode;
    private final String description;
}
