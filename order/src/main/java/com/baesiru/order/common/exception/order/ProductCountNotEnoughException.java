package com.baesiru.order.common.exception.order;

import com.baesiru.global.errorcode.ErrorCode;

public class ProductCountNotEnoughException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public ProductCountNotEnoughException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public ProductCountNotEnoughException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public ProductCountNotEnoughException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public ProductCountNotEnoughException(ErrorCode errorCode, Throwable throwable,
                                  String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
