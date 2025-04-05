package com.baesiru.user.common.exception.user;

import com.baesiru.global.errorcode.ErrorCode;

public class UserUnregisteredException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public UserUnregisteredException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public UserUnregisteredException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public UserUnregisteredException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public UserUnregisteredException(ErrorCode errorCode, Throwable throwable,
                                 String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
