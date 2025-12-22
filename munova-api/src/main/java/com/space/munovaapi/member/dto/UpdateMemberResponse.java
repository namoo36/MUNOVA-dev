package com.space.munovaapi.member.dto;

public record UpdateMemberResponse(
        String accessToken,
        String refreshToken
) {

    public static UpdateMemberResponse of(String accessToken, String refreshToken) {
        return new UpdateMemberResponse(accessToken, refreshToken);
    }
}
