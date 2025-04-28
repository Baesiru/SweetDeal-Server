package com.baesiru.order.common.exception.order;

import com.baesiru.global.errorcode.ErrorCode;

public class UserNotEqualException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public UserNotEqualException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public UserNotEqualException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public UserNotEqualException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public UserNotEqualException(ErrorCode errorCode, Throwable throwable,
                                  String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
