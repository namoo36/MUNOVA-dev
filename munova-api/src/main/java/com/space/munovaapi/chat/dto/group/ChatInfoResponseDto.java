package com.space.munovaapi.chat.dto.group;

import com.space.munovaapi.chat.entity.Chat;
import com.space.munovaapi.chat.enums.ChatStatus;

import java.time.LocalDateTime;

public record ChatInfoResponseDto(
        Long chatId,
        String name,
        int maxParticipant,
        ChatStatus status,
        LocalDateTime createdAt) {
    public static ChatInfoResponseDto of(Chat chat) {
        return new ChatInfoResponseDto(
                chat.getId(), chat.getName(), chat.getMaxParticipant(), chat.getStatus(), chat.getCreatedAt()
        );
    }
}
