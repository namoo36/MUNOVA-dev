package com.space.munovaapi.coupon.dto;

import jakarta.validation.constraints.NotNull;

public record UseCouponRequest(
        @NotNull(message = "원가 필수")
        Long originalPrice
) {
    public static UseCouponRequest of(Long originalPrice) {
        return new UseCouponRequest(originalPrice);
    }
}
