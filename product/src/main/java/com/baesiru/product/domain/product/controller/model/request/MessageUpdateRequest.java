package com.baesiru.product.domain.product.controller.model.request;

import com.baesiru.product.domain.product.service.model.order.OrderItemRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageUpdateRequest {
    private List<OrderItemRequest> orderItemRequests;
}
