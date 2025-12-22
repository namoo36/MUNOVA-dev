package com.space.munovaapi.chat.service;

import com.space.munovaapi.chat.dto.ChatItemDto;
import com.space.munovaapi.chat.dto.group.*;
import com.space.munovaapi.chat.dto.onetoone.OneToOneChatResponseDto;
import com.space.munovaapi.chat.enums.ChatUserType;
import com.space.munovaapi.member.dto.MemberRole;

import java.util.List;


public interface ChatRoomService {

    OneToOneChatResponseDto createOneToOneChatRoom(Long productId, Long memberId);

    List<ChatItemDto> getOneToOneChatRoomsByMember(ChatUserType chatUserType, Long memberId);

    GroupChatInfoResponseDto createGroupChatRoom(GroupChatRequestDto requestDto, Long memberId);

    List<GroupChatDetailResponseDto> searchGroupChatRooms(String keyword, List<Long> tagId, Boolean isMine, Long memberId);

    List<GroupChatDetailResponseDto> getMyGroupChatRooms(Long memberId);

//    List<ChatItemDto> getGroupChatRooms(Long memberId);

    List<ChatItemDto> getOneToOneChatRoomsBySeller(Long memberId);

//    List<ChatItemDto> getAllGroupChatRooms();

    ChatInfoResponseDto setChatRoomClosed(Long chatId, Long memberId, MemberRole role);

    ChatInfoResponseDto updateGroupChatInfo(Long chatId, GroupChatUpdateRequestDto groupChatUpdateDto, Long memberId);

    void leaveGroupChat(Long chatId, Long memberId);

    void joinGroupChat(Long chatId, Long memberId);

    void closeGroupChat(Long chatId, Long memberId);

    void openGroupChat(Long chatId, Long memberId);

    GroupChatDetailResponseDto getGroupChatDetail(Long chatId, Long memberId);
}
