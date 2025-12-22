package com.space.munovaapi.chat.dto.group;

import com.space.munovaapi.chat.entity.ChatMember;
import com.space.munovaapi.chat.enums.ChatUserType;

public record MemberInfoDto(
        Long memberId,
        String name,
        ChatUserType chatUserType

) {
    public static MemberInfoDto of(Long memberId, String name, ChatUserType chatUserType) {
        return new MemberInfoDto(memberId, name, chatUserType);
    }

    public static MemberInfoDto of(ChatMember cm) {
        return new MemberInfoDto(cm.getMemberIdValue(), cm.getName(), cm.getChatMemberType());
    }
}
