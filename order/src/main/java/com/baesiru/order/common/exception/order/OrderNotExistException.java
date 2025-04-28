package com.baesiru.order.common.exception.order;

import com.baesiru.global.errorcode.ErrorCode;

public class OrderNotExistException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public OrderNotExistException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public OrderNotExistException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public OrderNotExistException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public OrderNotExistException(ErrorCode errorCode, Throwable throwable,
                                  String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
