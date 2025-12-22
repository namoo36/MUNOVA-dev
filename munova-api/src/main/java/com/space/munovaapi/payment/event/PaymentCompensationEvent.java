package com.space.munovaapi.payment.event;

public record PaymentCompensationEvent(
        String paymentKey,
        String orderNum,
        Long amount

) {
}
