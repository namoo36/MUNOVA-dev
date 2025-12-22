package com.space.munovaapi.payment.dto;

public record CancelPaymentRequest(
        CancelReason cancelReason,
        Long cancelAmount
) {
    public static CancelPaymentRequest of (CancelReason cancelReason, Long cancelAmount) {
        return new CancelPaymentRequest(cancelReason, cancelAmount);
    }
}
