package com.space.munovaapi.order.dto;

import com.space.munovaapi.order.exception.OrderItemException;

public record OrderItemRequest(
        Long productDetailId,
        Integer quantity
) {
    public OrderItemRequest {
        if (productDetailId == null || productDetailId <= 0) {
            throw OrderItemException.invalidItemId();
        }

        if (quantity == null || quantity <= 0) {
            throw OrderItemException.invalidQuantity("현재 수량: " + quantity);
        }
    }
}
