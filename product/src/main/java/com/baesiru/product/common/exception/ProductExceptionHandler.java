package com.baesiru.product.common.exception;

import com.baesiru.global.api.Api;
import com.baesiru.product.common.errorcode.ProductErrorCode;
import com.baesiru.product.common.exception.product.ProductDeleteDenyException;
import com.baesiru.product.common.exception.product.ProductNotFoundException;
import com.baesiru.product.common.exception.product.UnauthorizedStoreAccessException;
import com.baesiru.product.common.exception.product.WrongProductInformationException;
import com.baesiru.product.domain.product.repository.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ProductExceptionHandler {
    @ExceptionHandler(value = UnauthorizedStoreAccessException.class)
    public ResponseEntity<Api<Object>> UnauthorizedStoreAccessException(UnauthorizedStoreAccessException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Api.ERROR(ProductErrorCode.UNAUTHORIZED_STORE_ACCESS));
    }

    @ExceptionHandler(value = WrongProductInformationException.class)
    public ResponseEntity<Api<Object>> wrongProductInformationExceptionException(WrongProductInformationException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Api.ERROR(ProductErrorCode.WRONG_PRODUCT_INFORMATION));
    }

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<Api<Object>> productNotFoundException(ProductNotFoundException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Api.ERROR(ProductErrorCode.PRODUCT_NOT_FOUND));
    }

    @ExceptionHandler(value = ProductDeleteDenyException.class)
    public ResponseEntity<Api<Object>> productDeleteDenyException(ProductDeleteDenyException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Api.ERROR(ProductErrorCode.PRODUCT_DELETE_DENY));
    }
}
