package com.space.munovaapi.member.dto;

import com.space.munovaapi.core.utils.ValidEnum;

public record UpdateMemberRequest(
        String username,
        String address,
        @ValidEnum(enumClass = MemberRole.class)
        String role
) {
}
