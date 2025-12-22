package com.space.munovaapi.auth.dto;

import com.space.munovaapi.member.dto.MemberRole;

public record SignInResponse(
        Long memberId,
        String username,
        String accessToken,
        String refreshToken,
        MemberRole role
) {
    public static SignInResponse from(SignInGenerateToken signInGenerateToken) {
        return new SignInResponse(
                signInGenerateToken.memberId(),
                signInGenerateToken.username(),
                signInGenerateToken.accessToken(),
                signInGenerateToken.refreshToken(),
                signInGenerateToken.role()
        );
    }
}
