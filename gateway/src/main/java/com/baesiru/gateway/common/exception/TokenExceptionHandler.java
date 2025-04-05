package com.baesiru.gateway.common.exception;

import com.baesiru.gateway.common.errorcode.TokenErrorCode;
import com.baesiru.gateway.common.exception.token.NotPermittedException;
import com.baesiru.gateway.common.exception.token.TokenException;
import com.baesiru.gateway.common.exception.token.TokenExpiredException;
import com.baesiru.gateway.common.exception.token.TokenSignatureException;
import com.baesiru.global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TokenExceptionHandler {
    @ExceptionHandler(value = TokenException.class)
    public ResponseEntity<Api<Object>> tokenException(TokenException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Api.ERROR(TokenErrorCode.TOKEN_EXCEPTION));
    }

    @ExceptionHandler(value = TokenSignatureException.class)
    public ResponseEntity<Api<Object>> tokenSignatureException(TokenSignatureException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Api.ERROR(TokenErrorCode.INVALID_TOKEN));
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<Api<Object>> tokenExpiredException(TokenExpiredException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Api.ERROR(TokenErrorCode.EXPIRED_TOKEN));
    }

    @ExceptionHandler(value = NotPermittedException.class)
    public ResponseEntity<Api<Object>> notPermittedException(NotPermittedException e) {
        log.warn("", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Api.ERROR(TokenErrorCode.NOT_PERMITTED));
    }

}
