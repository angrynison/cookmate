package com.cookmate.cookmate.member;

import com.cookmate.global.security.JwtProvider;
import com.cookmate.global.type.Cuisine;
import com.cookmate.global.type.Role;
import com.cookmate.member.domain.Member;
import com.cookmate.member.dto.MemberRequestDto;
import com.cookmate.member.dto.MemberResponseDto;
import com.cookmate.member.repository.MemberRepository;
import com.cookmate.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class JwtProviderTest {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;



    @Test
    void jwtTest() {

        Set<Cuisine> cuisine = new HashSet<>();
        cuisine.add(Cuisine.한식);
        cuisine.add(Cuisine.중식);

        // 회원가입 요청 생성
        MemberRequestDto.JoinRequest joinRequest = new MemberRequestDto.JoinRequest(
                "kalina",
                "a12345",
                "앵그리",
                true,
                "a8b3c9d2e4f7g1h6i0j5k9l2m8n4o7p1q6r0s5"
        );

        Long memberId = memberService.join(joinRequest);
        Member member = memberRepository.findById(memberId).
                orElseThrow(() -> new IllegalArgumentException("memberId not found"));

        MemberRequestDto.ProfileRequest profileRequest = new MemberRequestDto.ProfileRequest(
                Member.Sex.남,
                cuisine,
                15
        );

        // 로그인 요청 생성
        MemberRequestDto.LoginRequest loginRequest = new MemberRequestDto.LoginRequest(
                "kalina",
                "a12345"
        );

        MemberResponseDto.JwtTokenResponse token = memberService.login(loginRequest);
        System.out.println(token);

        Long validId = jwtProvider.getMemberIdFromToken(token.accessToken());
        String role = jwtProvider.getRoleFromToken(token.accessToken());
        Boolean isValid = jwtProvider.validateToken(token.accessToken());

        assertThat(validId).isEqualTo(memberId);
        assertThat(role).isEqualTo("ADMIN");
        assertThat(isValid).isTrue();
    }













}
