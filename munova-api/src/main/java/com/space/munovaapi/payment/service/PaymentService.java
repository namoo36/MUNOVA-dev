package com.space.munovaapi.payment.service;

import com.space.munovaapi.order.dto.CancelOrderItemRequest;
import com.space.munovaapi.payment.dto.ConfirmPaymentRequest;
import com.space.munovaapi.payment.entity.Payment;

public interface PaymentService {
    void confirmPaymentAndSavePayment(ConfirmPaymentRequest requestBody, Long memberId);
    Payment getPaymentByOrderId(Long orderId);
    void cancelPaymentAndSaveRefund(Long orderItemId, Long orderId, CancelOrderItemRequest request);
}