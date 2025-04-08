package com.baesiru.product.common.exception.product;

import com.baesiru.global.errorcode.ErrorCode;

public class WrongProductInformationException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public WrongProductInformationException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public WrongProductInformationException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public WrongProductInformationException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public WrongProductInformationException(ErrorCode errorCode, Throwable throwable,
                                            String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
