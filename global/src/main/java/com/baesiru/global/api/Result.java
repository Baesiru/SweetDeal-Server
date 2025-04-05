package com.baesiru.global.api;

import ch.qos.logback.core.spi.ErrorCodes;
import com.baesiru.global.api.enums.ResultStatus;
import com.baesiru.global.errorcode.CommonErrorCode;
import com.baesiru.global.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {
    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    public static Result OK(){
        return Result.builder()
                .resultCode(CommonErrorCode.OK.getHttpCode())
                .resultMessage(CommonErrorCode.OK.getDescription())
                .resultDescription(ResultStatus.SUCCESS.getDescription())
                .build();
    }


    public static Result ERROR(ErrorCode errorCode){
        return Result.builder()
                .resultCode(errorCode.getHttpCode())
                .resultMessage(errorCode.getDescription())
                .resultDescription(ResultStatus.FAIL.getDescription())
                .build();
    }

    public static Result ERROR(ErrorCode errorCode, Throwable tx){
        return Result.builder()
                .resultCode(errorCode.getHttpCode())
                .resultMessage(errorCode.getDescription())
                .resultDescription(tx.getMessage())
                .build();
    }

    public static Result ERROR(ErrorCode errorCode, String description){
        return Result.builder()
                .resultCode(errorCode.getHttpCode())
                .resultMessage(errorCode.getDescription())
                .resultDescription(description)
                .build();
    }
}
