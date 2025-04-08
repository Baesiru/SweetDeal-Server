package com.baesiru.image.common.exception;

import com.baesiru.global.api.Api;
import com.baesiru.global.errorcode.UserErrorCode;
import com.baesiru.image.common.errorcode.ImageErrorCode;
import com.baesiru.image.common.exception.image.ImageDeleteException;
import com.baesiru.image.common.exception.image.ImageDirectoryErrorException;
import com.baesiru.image.common.exception.image.ImageNotFoundException;
import com.baesiru.image.common.exception.image.ImageUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ImageExceptionHandler {
    @ExceptionHandler(value = ImageUploadException.class)
    public ResponseEntity<Api<Object>> imageUploadException(ImageUploadException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Api.ERROR(ImageErrorCode.IMAGE_UPLOAD_ERROR));
    }

    @ExceptionHandler(value = ImageDeleteException.class)
    public ResponseEntity<Api<Object>> imageDeleteException(ImageDeleteException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Api.ERROR(ImageErrorCode.IMAGE_DELETE_ERROR));
    }

    @ExceptionHandler(value = ImageNotFoundException.class)
    public ResponseEntity<Api<Object>> imageNotFoundException(ImageNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Api.ERROR(ImageErrorCode.IMAGE_NOT_FOUND));
    }

    @ExceptionHandler(value = ImageDirectoryErrorException.class)
    public ResponseEntity<Api<Object>> imageDirectoryErrorException(ImageDirectoryErrorException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Api.ERROR(ImageErrorCode.IMAGE_DIRECTORY_ERROR));
    }
}
