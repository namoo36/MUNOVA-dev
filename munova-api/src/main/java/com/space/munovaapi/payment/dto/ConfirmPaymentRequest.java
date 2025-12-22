package com.space.munovaapi.payment.dto;

public record ConfirmPaymentRequest(
        String paymentKey,
        String orderId,
        Long amount
) {
}
