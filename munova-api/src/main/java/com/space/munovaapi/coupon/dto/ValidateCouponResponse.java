package com.space.munovaapi.coupon.dto;

public record ValidateCouponResponse(
        Boolean isValid
) {
    public static ValidateCouponResponse of(Boolean isValid) {
        return new ValidateCouponResponse(isValid);
    }
}
