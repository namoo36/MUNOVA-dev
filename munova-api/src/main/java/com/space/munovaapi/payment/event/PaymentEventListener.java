package com.space.munovaapi.payment.event;

import com.space.munovaapi.payment.client.TossApiClient;
import com.space.munovaapi.payment.dto.*;
import com.space.munovaapi.payment.entity.Refund;
import com.space.munovaapi.payment.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final TossApiClient tossApiClient;
    private final RefundRepository refundRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void onPaymentRollback(PaymentCompensationEvent event) {
        String paymentKey = event.paymentKey();

        if (refundRepository.existsByPaymentKey(paymentKey)) {
            return;
        }

        TossPaymentResponse response = tossApiClient.sendCancelRequest(paymentKey,
                CancelPaymentRequest.of(CancelReason.ROLLBACK_COMPENSATION, event.amount()));

        for(CancelDto cancel : response.cancels()) {

            if (cancel.cancelStatus().isDone()) {
                refundRepository.save(Refund.createWhenRollBack(paymentKey, cancel));
            }
        }
    }
}
