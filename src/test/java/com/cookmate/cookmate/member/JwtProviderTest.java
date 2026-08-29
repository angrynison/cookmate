package com.cookmate.cookmate.member;

import com.cookmate.global.security.JwtProvider;
import com.cookmate.global.type.Cuisine;
import com.cookmate.member.domain.Member;
import com.cookmate.member.dto.MemberRequestDto;
import com.cookmate.member.repository.MemberRepository;
import com.cookmate.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class JwtProviderTest {

    @MockitoBean
    JwtProvider jwtProvider;

    @MockitoBean
    MemberRepository memberRepository;
    @MockitoBean
    MemberService memberService;

    Set<Cuisine> cuisine = new HashSet<>();
    cuisine.add(Cuisine.한식);
    cuisine.add(Cuisine.중식);


    @Test
    void jwtTest() {
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

    }













}
