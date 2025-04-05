package com.baesiru.user.common.exception.token;

import com.baesiru.global.errorcode.ErrorCode;

public class NotPermittedException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public NotPermittedException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public NotPermittedException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public NotPermittedException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public NotPermittedException(ErrorCode errorCode, Throwable throwable,
                          String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
