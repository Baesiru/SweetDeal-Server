package com.baesiru.store.common.exception;

import com.baesiru.global.api.Api;
import com.baesiru.store.common.errorcode.StoreErrorCode;
import com.baesiru.store.common.exception.store.BusinessNumberExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class StoreExceptionHandler {
    @ExceptionHandler(value = BusinessNumberExistsException.class)
    public ResponseEntity<Api<Object>> tokenException(BusinessNumberExistsException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Api.ERROR(StoreErrorCode.EXISTS_BUSINESS_NUMBER));
    }
}
