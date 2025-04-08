package com.baesiru.product.common.exception.product;

import com.baesiru.global.errorcode.ErrorCode;

public class UnauthorizedStoreAccessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public UnauthorizedStoreAccessException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public UnauthorizedStoreAccessException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public UnauthorizedStoreAccessException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public UnauthorizedStoreAccessException(ErrorCode errorCode, Throwable throwable,
                                         String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
