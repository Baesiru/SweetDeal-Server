package com.baesiru.order.common.exception;

import com.baesiru.global.api.Api;
import com.baesiru.order.common.errorcode.OrderErrorCode;
import com.baesiru.order.common.exception.order.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OrderExceptionHandler {
    @ExceptionHandler(value = ExpiredException.class)
    public ResponseEntity<Api<Object>> expiredException(ExpiredException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Api.ERROR(OrderErrorCode.EXPIRED_ERROR));
    }

    @ExceptionHandler(value = OrderNotExistException.class)
    public ResponseEntity<Api<Object>> orderNotExistException(OrderNotExistException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Api.ERROR(OrderErrorCode.ORDER_NOT_EXIST));
    }

    @ExceptionHandler(value = ProductCountNotEnoughException.class)
    public ResponseEntity<Api<Object>> productCountNotEnoughException(ProductCountNotEnoughException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Api.ERROR(OrderErrorCode.PRODUCT_COUNT_NOT_ENOUGH));
    }

    @ExceptionHandler(value = ProductNotExistException.class)
    public ResponseEntity<Api<Object>> productNotExistException(ProductNotExistException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Api.ERROR(OrderErrorCode.PRODUCT_NOT_EXIST));
    }

    @ExceptionHandler(value = StoreNotExistException.class)
    public ResponseEntity<Api<Object>> storeNotExistException(StoreNotExistException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Api.ERROR(OrderErrorCode.STORE_NOT_EXIST));
    }

    @ExceptionHandler(value = UserNotEqualException.class)
    public ResponseEntity<Api<Object>> userNotEqualException(UserNotEqualException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Api.ERROR(OrderErrorCode.USER_NOT_EQUAL));
    }
}
