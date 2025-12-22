package com.space.munovaapi.coupon.dto;

import com.space.munovaapi.core.utils.ValidEnum;

public record SearchCouponParams(
        @ValidEnum(enumClass = CouponStatus.class)
        String status
) {
}
