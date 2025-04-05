package com.baesiru.user.common.exception.user;

import com.baesiru.global.errorcode.ErrorCode;

public class EmailExistsException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public EmailExistsException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public EmailExistsException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public EmailExistsException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public EmailExistsException(ErrorCode errorCode, Throwable throwable,
                                   String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
