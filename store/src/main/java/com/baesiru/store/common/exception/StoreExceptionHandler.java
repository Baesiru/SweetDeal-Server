package com.baesiru.store.common.exception;

import com.baesiru.global.api.Api;
import com.baesiru.store.common.errorcode.StoreErrorCode;
import com.baesiru.store.common.exception.store.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class StoreExceptionHandler {
    @ExceptionHandler(value = BusinessNumberExistsException.class)
    public ResponseEntity<Api<Object>> businessNumberExistsException(BusinessNumberExistsException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Api.ERROR(StoreErrorCode.EXISTS_BUSINESS_NUMBER));
    }

    @ExceptionHandler(value = StoreNotFoundException.class)
    public ResponseEntity<Api<Object>> storeNotFoundException(StoreNotFoundException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Api.ERROR(StoreErrorCode.STORE_NOT_FOUND));
    }

    @ExceptionHandler(value = FailUnregisterStoreException.class)
    public ResponseEntity<Api<Object>> failUnregisterStoreException(FailUnregisterStoreException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Api.ERROR(StoreErrorCode.FAIL_UNREGISTER_STORE));
    }

    @ExceptionHandler(value = FailRegisterStoreException.class)
    public ResponseEntity<Api<Object>> failRegisterStoreException(FailRegisterStoreException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Api.ERROR(StoreErrorCode.FAIL_REGISTER_STORE));
    }

    @ExceptionHandler(value = FailFetchStoreException.class)
    public ResponseEntity<Api<Object>> failFetchStoreException(FailFetchStoreException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Api.ERROR(StoreErrorCode.FAIL_FETCH_STORE));
    }
}
