package com.space.munovaapi.member.controller;

import com.space.munovaapi.core.config.ResponseApi;
import com.space.munovaapi.member.dto.GetMemberResponse;
import com.space.munovaapi.member.dto.UpdateMemberRequest;
import com.space.munovaapi.member.dto.UpdateMemberResponse;
import com.space.munovaapi.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.space.munovaapi.core.config.StaticVariables.DEVICE_ID_HEADER_PREFIX;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 유저 정보 조회
     */
    @GetMapping("/{memberId}")
    public ResponseApi<GetMemberResponse> getMember(@PathVariable Long memberId) {
        GetMemberResponse member = memberService.getMember(memberId);
        return ResponseApi.ok(member);
    }

    /**
     * 유저 정보 변경
     */
    @PatchMapping("/{memberId}")
    public ResponseApi<UpdateMemberResponse> updateMember(
            @PathVariable Long memberId,
            @Valid @RequestBody UpdateMemberRequest updateMemberRequest,
            @RequestHeader(value = DEVICE_ID_HEADER_PREFIX) String deviceId
    ) {
        UpdateMemberResponse updateMemberResponse = memberService.updateMember(memberId, updateMemberRequest, deviceId);
        return ResponseApi.ok(updateMemberResponse);
    }

}
