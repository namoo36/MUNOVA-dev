package com.space.munovaapi.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.space.munovaapi.payment.entity.PaymentMethod;
import com.space.munovaapi.payment.entity.PaymentStatus;
import com.space.munovaapi.payment.exception.PaymentException;

import java.time.ZonedDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TossPaymentResponse (
        String paymentKey,
        String orderId,
        PaymentStatus status,
        PaymentMethod method,
        Long totalAmount,
        ZonedDateTime requestedAt,
        ZonedDateTime approvedAt,
        ReceiptInfo receipt,
        String lastTransactionKey,
        List<CancelDto> cancels
){
    public TossPaymentResponse {
        if (paymentKey == null || paymentKey.isBlank()) {
            throw PaymentException.invalidTossResponse("paymentKey는 필수 항목입니다.");
        }
        if (orderId == null || orderId.isBlank()) {
            throw PaymentException.invalidTossResponse("orderId는 필수 항목입니다.");
        }
        if (status == null) {
            throw PaymentException.invalidTossResponse("status는 필수 항목입니다.");
        }
        if (totalAmount == null || totalAmount < 0) {
            throw PaymentException.invalidTossResponse("totalAmount는 0보다 크거나 같아야 합니다.");
        }
        if (requestedAt == null) {
            throw PaymentException.invalidTossResponse("requestedAt은 필수 항목입니다.");
        }
    }
}
