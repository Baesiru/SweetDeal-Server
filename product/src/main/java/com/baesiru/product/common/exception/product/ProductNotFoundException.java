package com.baesiru.product.common.exception.product;

import com.baesiru.global.errorcode.ErrorCode;

public class ProductNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public ProductNotFoundException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public ProductNotFoundException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public ProductNotFoundException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public ProductNotFoundException(ErrorCode errorCode, Throwable throwable,
                                            String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
