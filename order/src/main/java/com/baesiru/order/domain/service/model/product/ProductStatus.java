package com.baesiru.order.domain.service.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    SALE("판매중"),
    RESERVED("예약중"),
    SOLD("판매완료"),
    DELETED("삭제됨");

    private final String description;
}
