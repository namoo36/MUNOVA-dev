package com.space.munovaapi.member.service;

import com.space.munovaapi.member.dto.GetMemberResponse;
import com.space.munovaapi.member.dto.UpdateMemberRequest;
import com.space.munovaapi.member.dto.UpdateMemberResponse;
import com.space.munovaapi.member.entity.Member;

public interface MemberService {

    // 유저 정보 조회
    GetMemberResponse getMember(Long memberId);

    // 유저 정보 변경
    UpdateMemberResponse updateMember(Long memberId, UpdateMemberRequest updateMemberRequest, String deviceId);

    Member getMemberEntity(Long memberId);
}
