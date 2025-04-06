package com.baesiru.store.common.exception.store;

import com.baesiru.global.errorcode.ErrorCode;

public class BusinessNumberExistsException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public BusinessNumberExistsException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public BusinessNumberExistsException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public BusinessNumberExistsException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public BusinessNumberExistsException(ErrorCode errorCode, Throwable throwable,
                                String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
