package com.baesiru.image.common.exception.image;

import com.baesiru.global.errorcode.ErrorCode;

public class ImageNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public ImageNotFoundException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public ImageNotFoundException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public ImageNotFoundException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public ImageNotFoundException(ErrorCode errorCode, Throwable throwable,
                                        String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
