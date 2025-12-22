package com.space.munovaapi.notification.controller;

import com.space.munovaapi.core.config.ResponseApi;
import com.space.munovaapi.core.dto.PagingResponse;
import com.space.munovaapi.notification.dto.NotificationResponse;
import com.space.munovaapi.notification.dto.SearchNotificationUnreadResponse;
import com.space.munovaapi.notification.service.NotificationService;
import com.space.munovaapi.security.jwt.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // SSE 구독
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        Long memberId = JwtHelper.getMemberId();
        return notificationService.subscribe(memberId);
    }

    // 알림 목록 조회
    @GetMapping
    public ResponseApi<PagingResponse<NotificationResponse>> searchNotifications(
            @PageableDefault Pageable pageable,
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Sort sort
    ) {
        Long memberId = JwtHelper.getMemberId();
        PagingResponse<NotificationResponse> notifications
                = notificationService.searchNotifications(pageable, sort, memberId);
        return ResponseApi.ok(notifications);
    }

    // 읽지 않은 알림 개수
    @GetMapping("/unread/count")
    public ResponseApi<SearchNotificationUnreadResponse> searchCountUnreadNotification() {
        Long memberId = JwtHelper.getMemberId();
        long unreadCount = notificationService.getUnreadCount(memberId);
        SearchNotificationUnreadResponse response = SearchNotificationUnreadResponse.of(unreadCount);
        return ResponseApi.ok(response);
    }

    // 알림 읽음 처리
    @PatchMapping("/read/{notificationId}")
    public ResponseApi<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseApi.ok();
    }
}
