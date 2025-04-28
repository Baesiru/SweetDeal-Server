package com.baesiru.order.domain.controller;

import com.baesiru.global.annotation.AuthenticatedUser;
import com.baesiru.global.api.Api;
import com.baesiru.global.resolver.AuthUser;
import com.baesiru.order.common.response.MessageResponse;
import com.baesiru.order.domain.business.OrderBusiness;
import com.baesiru.order.domain.controller.model.request.OrderCreateRequest;
import com.baesiru.order.domain.controller.model.request.PaymentRequest;
import com.baesiru.order.domain.controller.model.response.OrderCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderApiController {
    @Autowired
    private OrderBusiness orderBusiness;

    @PostMapping("/order")
    public Api<?> createOrder(@RequestBody OrderCreateRequest orderCreateRequest,
                              @AuthenticatedUser AuthUser authUser) {
        OrderCreateResponse response = orderBusiness.createOrder(orderCreateRequest, authUser);
        return Api.OK(response);
    }

    @PostMapping("/payment")
    public Api<?> payment(@RequestBody PaymentRequest paymentRequest,
                          @AuthenticatedUser AuthUser authUser) {
        MessageResponse response = orderBusiness.payment(paymentRequest, authUser);
        return Api.OK(response);
    }
}
