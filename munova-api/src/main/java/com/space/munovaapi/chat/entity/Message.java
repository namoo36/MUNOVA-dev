package com.space.munovaapi.chat.entity;

import com.space.munovaapi.chat.enums.MessageType;
import com.space.munovaapi.core.entity.BaseEntity;
import com.space.munovaapi.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "message")
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private MessageType type;   // TEXT, IMAGE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member userId;

    public static Message createMessage(String content, MessageType type, Chat chat, Member userId) {
        return Message.builder()
                .content(content)
                .type(type)
                .chatId(chat)
                .userId(userId)
                .build();
    }

}
