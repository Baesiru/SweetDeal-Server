package com.baesiru.image.common.errorcode;

import com.baesiru.global.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ImageErrorCode implements ErrorCode {
    IMAGE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "이미지를 저장할 수 없습니다."),
    IMAGE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "이미지를 삭제할 수 없습니다."),
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "요청하신 이미지를 찾을 수 없습니다."),
    IMAGE_DIRECTORY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "파일이 저장될 디렉터리를 생성할 수 없습니다.")
    ;

    private final Integer httpCode;
    private final String description;
}
