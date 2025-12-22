package com.space.munovaapi.chat.dto.message;

import com.space.munovaapi.chat.enums.MessageType;

public record ChatMessageRequestDto(

        Long senderId,
        MessageType messageType,
        String content
) {
    public static ChatMessageRequestDto of(Long senderId, MessageType messageType, String content) {
        return new ChatMessageRequestDto(senderId, messageType, content);
    }
}
