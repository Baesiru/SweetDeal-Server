package com.baesiru.image.common.exception.image;

import com.baesiru.global.errorcode.ErrorCode;

public class ImageUploadException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public ImageUploadException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public ImageUploadException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public ImageUploadException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public ImageUploadException(ErrorCode errorCode, Throwable throwable,
                                  String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
