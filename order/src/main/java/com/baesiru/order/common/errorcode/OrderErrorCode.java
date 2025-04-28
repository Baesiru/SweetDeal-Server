package com.baesiru.order.common.errorcode;

import com.baesiru.global.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements ErrorCode {
    USER_NOT_EQUAL(404, "유저 정보가 다릅니다."),
    PRODUCT_NOT_EXIST(404, "상품이 존재하지 않습니다."),
    PRODUCT_COUNT_NOT_ENOUGH(404, "상품 개수가 모자랍니다."),
    EXPIRED_ERROR(404, "유효기간이 지났습니다."),
    STORE_NOT_EXIST(404, "가게가 존재하지 않습니다."),
    ORDER_NOT_EXIST(404, "주문이 존재하지 않습니다.")
    ;
    private final Integer httpCode;
    private final String description;
}
