package com.space.munovaapi.coupon.dto;

import com.space.munovaapi.notification.common.NotificationMessage;
import com.space.munovaapi.notification.dto.NotificationType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CouponNotification implements NotificationMessage {
    COUPON_ISSUED("쿠폰발급안내", "[쿠폰발급] %s 발급되었습니다.\n유효기간 %s", "/mypage?activeTab=coupon");

    private final String title;
    private final String message;
    private final String redirectUrl;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.COUPON;
    }
}
