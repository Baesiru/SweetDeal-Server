package com.baesiru.gateway.common.exception.token;

import com.baesiru.global.errorcode.ErrorCode;

public class TokenSignatureException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public TokenSignatureException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public TokenSignatureException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public TokenSignatureException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public TokenSignatureException(ErrorCode errorCode, Throwable throwable,
                                 String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
