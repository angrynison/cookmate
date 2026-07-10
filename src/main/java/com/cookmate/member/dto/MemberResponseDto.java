package com.cookmate.member.dto;

import com.cookmate.global.type.Cuisine;
import com.cookmate.member.domain.Member;

import java.util.Set;

public class MemberResponseDto {
    
    // 회원,관리자 로그인 - jwt 반환
    public record JwtTokenResponse(
            String accessToken
    ) {
    }

    // 회원 등록,수정 - 회원번호 반환
    public record SignupResponse(
            Long id
    ) {
    }

    // 회원정보 조회 Dto
    public record MemberProfileResponse(
            String loginId,
            String name,
            Member.Sex sex,
            Set<Cuisine> cuisines
    ) {
        public static MemberProfileResponse from(Member member) {
            return new MemberProfileResponse(
                    member.getLoginId(),
                    member.getName(),
                    member.getSex(),
                    member.getCuisines()
            );
        }
    }
}
