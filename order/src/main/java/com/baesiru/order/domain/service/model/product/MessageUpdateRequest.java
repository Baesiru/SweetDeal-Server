package com.baesiru.order.domain.service.model.product;

import com.baesiru.order.domain.controller.model.request.OrderItemRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageUpdateRequest {
    List<OrderItemRequest> orderItemRequests;
}
