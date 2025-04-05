package com.baesiru.user.common.exception.token;

import com.baesiru.global.errorcode.ErrorCode;

public class TokenException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public TokenException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public TokenException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public TokenException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public TokenException(ErrorCode errorCode, Throwable throwable,
                          String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
