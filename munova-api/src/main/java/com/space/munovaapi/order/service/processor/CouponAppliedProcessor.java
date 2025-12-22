package com.space.munovaapi.order.service.processor;

import com.space.munovaapi.common.validation.AmountVerifier;
import com.space.munovaapi.coupon.dto.UseCouponRequest;
import com.space.munovaapi.coupon.dto.UseCouponResponse;
import com.space.munovaapi.coupon.service.CouponService;
import com.space.munovaapi.order.dto.CreateOrderRequest;
import com.space.munovaapi.order.entity.Order;
import com.space.munovaapi.order.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponAppliedProcessor implements OrderAmountProcessor {

    private final CouponService couponService;

    @Override
    public void process(Order order, CreateOrderRequest request, long totalAmount) {
        UseCouponRequest couponRequest = UseCouponRequest.of(totalAmount);
        UseCouponResponse couponResponse = couponService.calculateAmountWithCoupon(request.orderCouponId(), couponRequest);

        try {
            AmountVerifier.verify(request.clientCalculatedAmount(), couponResponse.finalPrice());
        } catch (IllegalArgumentException e) {
            throw OrderException.amountMismatchException(e.getMessage());
        }

        order.updateOrder(
                couponResponse.originalPrice(),
                couponResponse.discountPrice(),
                couponResponse.finalPrice(),
                request.orderCouponId()
        );
    }
}
