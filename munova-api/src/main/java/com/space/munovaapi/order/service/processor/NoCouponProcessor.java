package com.space.munovaapi.order.service.processor;

import com.space.munovaapi.common.validation.AmountVerifier;
import com.space.munovaapi.order.dto.CreateOrderRequest;
import com.space.munovaapi.order.entity.Order;
import com.space.munovaapi.order.exception.OrderException;
import org.springframework.stereotype.Component;

@Component
public class NoCouponProcessor implements OrderAmountProcessor {

    @Override
    public void process(Order order, CreateOrderRequest request, long totalAmount) {

        try {
            AmountVerifier.verify(request.clientCalculatedAmount(), totalAmount);
        } catch (IllegalArgumentException e) {
            throw OrderException.amountMismatchException(e.getMessage());
        }

        order.updateOrder(
                totalAmount,
                0L,
                totalAmount,
                null
        );
    }
}
