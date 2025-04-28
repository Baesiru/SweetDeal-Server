package com.baesiru.order.common.exception.order;

import com.baesiru.global.errorcode.ErrorCode;

public class ProductNotExistException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public ProductNotExistException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public ProductNotExistException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public ProductNotExistException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public ProductNotExistException(ErrorCode errorCode, Throwable throwable,
                                          String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
