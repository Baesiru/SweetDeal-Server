package com.baesiru.image.common.exception.image;

import com.baesiru.global.errorcode.ErrorCode;

public class ImageDirectoryErrorException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;

    public ImageDirectoryErrorException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public ImageDirectoryErrorException(ErrorCode errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.description = errorDescription;
    }

    public ImageDirectoryErrorException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

    public ImageDirectoryErrorException(ErrorCode errorCode, Throwable throwable,
                                String errorDescription) {
        super(throwable);
        this.errorCode = errorCode;
        this.description = errorDescription;
    }
}
