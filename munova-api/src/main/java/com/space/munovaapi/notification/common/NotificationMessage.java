package com.space.munovaapi.notification.common;

import com.space.munovaapi.notification.dto.NotificationType;

public interface NotificationMessage {
    String getTitle();

    String getMessage();

    String getRedirectUrl();

    NotificationType getNotificationType();

    default String format(Object... args) {
        if (args.length == 0) {
            return getMessage();
        }
        return String.format(getMessage(), args);
    }
}
