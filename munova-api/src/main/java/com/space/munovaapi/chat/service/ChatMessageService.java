package com.space.munovaapi.chat.service;

import com.space.munovaapi.chat.dto.message.ChatMessageRequestDto;
import com.space.munovaapi.chat.dto.message.ChatMessageResponseDto;
import com.space.munovaapi.chat.dto.message.ChatMessageViewDto;

import java.util.List;

public interface ChatMessageService {

    ChatMessageResponseDto createChatMessage(ChatMessageRequestDto chatMessageRequest, Long chatId);

    List<ChatMessageViewDto> getMessagesByChatId(Long chatId);

}