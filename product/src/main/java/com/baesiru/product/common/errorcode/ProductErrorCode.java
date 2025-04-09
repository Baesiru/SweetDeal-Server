package com.baesiru.product.common.errorcode;

import com.baesiru.global.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements ErrorCode {
    UNAUTHORIZED_STORE_ACCESS(403, "가게에 권한이 없는 접근입니다."),
    WRONG_PRODUCT_INFORMATION(400, "상품 정보가 올바르지 않습니다."),
    PRODUCT_NOT_FOUND(404, "상품을 찾을 수 없습니다."),
    PRODUCT_DELETE_DENY(400, "예약되었거나 판매완료된 상품은 삭제할 수 없습니다.")
    ;
    private final Integer httpCode;
    private final String description;
}
