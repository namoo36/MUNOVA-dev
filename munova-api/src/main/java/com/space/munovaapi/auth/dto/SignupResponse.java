package com.space.munovaapi.auth.dto;

public record SignupResponse(
        Long memberId,
        String username
) {
    public static SignupResponse of(Long memberId, String username) {
        return new SignupResponse(memberId, username);
    }
}
