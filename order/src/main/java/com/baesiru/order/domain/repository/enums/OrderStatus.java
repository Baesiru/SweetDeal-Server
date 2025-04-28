package com.baesiru.order.domain.repository.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    ORDERED("주문 완료"),
    CANCELED("취소 완료"),
    COMPLETED("결제 완료");

    private final String description;
}
