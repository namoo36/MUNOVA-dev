package com.space.munovaapi.auth.dto;

public record SignInRequest(
        String username,
        String password
) {
    public static SignInRequest of(String username, String password) {
        return new SignInRequest(username, password);
    }
}
