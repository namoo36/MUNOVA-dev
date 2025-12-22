package com.space.munovaapi.coupon.dto;

public record RegisterCouponDetailResponse(Long couponDetailId) {

    public static RegisterCouponDetailResponse of(Long couponDetailId) {
        return new RegisterCouponDetailResponse(couponDetailId);
    }
}
