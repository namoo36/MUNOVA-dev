package com.space.munovaapi.order.controller;

import com.space.munovaapi.core.config.ResponseApi;
import com.space.munovaapi.order.dto.CancelOrderItemRequest;
import com.space.munovaapi.order.service.OrderItemService;
import com.space.munovaapi.security.jwt.JwtHelper;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping("/{orderItemId}/cancel")
    public ResponseApi<Void>  cancelOrder(@PathVariable Long orderItemId, @RequestBody CancelOrderItemRequest request) {
        Long memberId = JwtHelper.getMemberId();
        orderItemService.cancelOrderItem(orderItemId, request, memberId);
        return ResponseApi.ok();
    }
}
