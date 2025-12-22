package com.space.munovachattest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChatPayload {
    private String type;
    private Long chatId;
    private Long senderId;
    private String content;
    private long timestamp;
    private LocalDateTime createdAt;
}
