package com.baesiru.global.api;

import ch.qos.logback.core.spi.ErrorCodes;
import com.baesiru.global.errorcode.ErrorCode;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {
    private Result result;

    @Valid
    private T body;

    public static <T> Api<T> OK(T object){
        Api<T> api = new Api<T>();
        api.body = object;
        api.result = Result.OK();
        return api;
    }

    public static Api<Object> ERROR(Result result){
        Api<Object> api = new Api<Object>();
        api.result = result;
        return api;
    }

    public static Api<Object> ERROR(ErrorCode errorCode){
        Api<Object> api = new Api<Object>();
        api.result = Result.ERROR(errorCode);
        return api;
    }

    public static Api<Object> ERROR(ErrorCode errorCode,Throwable tx){
        Api<Object> api = new Api<Object>();
        api.result = Result.ERROR(errorCode,tx);
        return api;
    }

    public static Api<Object> ERROR(ErrorCode errorCode, String description){
        Api<Object> api = new Api<Object>();
        api.result = Result.ERROR(errorCode,description);
        return api;
    }

}
