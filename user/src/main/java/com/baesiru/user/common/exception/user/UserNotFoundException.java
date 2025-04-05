package com.baesiru.user.common.exception.user;

import com.baesiru.global.errorcode.ErrorCode;

public class UserNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public UserNotFoundException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public UserNotFoundException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public UserNotFoundException(ErrorCode errorCode, Throwable throwable,
                              String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
