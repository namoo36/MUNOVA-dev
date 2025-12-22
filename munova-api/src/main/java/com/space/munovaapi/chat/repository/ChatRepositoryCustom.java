package com.space.munovaapi.chat.repository;

import com.space.munovaapi.chat.entity.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatRepositoryCustom {

    List<Chat> findByNameAndTags(String keyword, List<Long> categoryIds, Long memberId, Boolean isMine);

    Optional<Chat> findGroupChatDetailById(Long chatId);
}
