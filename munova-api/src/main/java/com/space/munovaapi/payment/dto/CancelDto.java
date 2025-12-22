package com.space.munovaapi.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.space.munovaapi.payment.entity.CancelStatus;
import com.space.munovaapi.payment.exception.PaymentException;

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CancelDto(
        String transactionKey,
        String cancelReason,
        ZonedDateTime canceledAt,
        Long cancelAmount,
        CancelStatus cancelStatus
) {
    public CancelDto {
        if (transactionKey == null || transactionKey.isBlank()) {
            throw PaymentException.invalidTossResponse("CancelDto: transactionKey는 필수 항목입니다.");
        }
        if (cancelReason == null || cancelReason.isBlank()) {
            throw PaymentException.invalidTossResponse("CancelDto: cancelReason은 필수 항목입니다.");
        }
        if (canceledAt == null) {
            throw PaymentException.invalidTossResponse("CancelDto: canceledAt은 필수 항목입니다.");
        }
        if (cancelStatus == null) {
            throw PaymentException.invalidTossResponse("CancelDto: cancelStatus는 필수 항목입니다.");
        }
        if (cancelAmount == null || cancelAmount < 0) {
            throw PaymentException.invalidTossResponse("CancelDto: cancelAmount는 0보다 크거나 같아야 합니다.");
        }
    }
}
