package com.space.munovaapi.auth.service;

import com.space.munovaapi.auth.dto.SignInGenerateToken;
import com.space.munovaapi.auth.dto.SignInRequest;
import com.space.munovaapi.auth.dto.SignupRequest;
import com.space.munovaapi.auth.dto.SignupResponse;

public interface AuthService {

    // 회원가입
    SignupResponse signup(SignupRequest signupRequest);

    // 로그인
    SignInGenerateToken signIn(SignInRequest signInRequest, String deviceId);

    // 로그아웃
    void signOut(String deviceId, Long memberId);

    // 권한 확인
    void verifyAuthorization(Long actualOwnerId, Long currentMemberId);
}
