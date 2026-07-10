package com.cookmate.member.dto;

import com.cookmate.global.type.Cuisine;
import com.cookmate.member.domain.Member;

import java.util.Set;

public class MemberRequestDto {

    private MemberRequestDto() {}

    // 회원가입 dto
    public record JoinRequest(
            String loginId,
            String password,
            String name,
            boolean isAdmin,
            String adminToken
    ) {
    }

    // 로그인 dto
    public record LoginRequest(
            String loginId,
            String password
    ) {
    }

    // 최초 프로필 등록 dto
    public record MemberProfileRequest(
            Member.Sex sex,
            Set<Cuisine> cuisines
    ) {
    }

    // 회원 정보 수정 dto
    public record MemberEditRequest(
            String loginId,
            String password,
            String name
    ){
    }
}
