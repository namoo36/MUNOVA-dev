package com.space.munovaapi.order.dto;

import com.space.munovaapi.payment.dto.CancelReason;

public record CancelOrderItemRequest(
        CancelType cancelType,
        CancelReason cancelReason,
        Long cancelAmount
) {
}
