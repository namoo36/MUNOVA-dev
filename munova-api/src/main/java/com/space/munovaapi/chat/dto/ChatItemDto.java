package com.space.munovaapi.chat.dto;

import java.time.LocalDateTime;

public record ChatItemDto(
        Long chatId,

        String name,

        String lastMessageContent,

        LocalDateTime lastMessageTime
) {
    public static ChatItemDto of(Long chatId, String name, String lastMessageContent, LocalDateTime lastMessageTime) {
        return new ChatItemDto(chatId, name, lastMessageContent, lastMessageTime);
    }
}
