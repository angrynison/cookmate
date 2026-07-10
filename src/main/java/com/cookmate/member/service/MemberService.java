package com.cookmate.member.service;

import com.cookmate.member.dto.MemberRequestDto;
import com.cookmate.member.dto.MemberResponseDto;

public interface MemberService {

    // 회원 가입
    public Long join(MemberRequestDto.JoinRequest joinRequest);
    // 로그인
    public MemberResponseDto.JwtTokenResponse login(MemberRequestDto.LoginRequest loginRequest);
    // 로그아웃
    public MemberResponseDto.JwtTokenResponse logout();
    // 회원 정보 조회
    public MemberResponseDto.MemberProfileResponse getMyProfile(Long id);
    // 최초 프로필 생성
    public Long createProfile(Long id,MemberRequestDto.MemberProfileRequest memberProfileRequest);
    // 회원정보 수정
    public Long updateMember(Long id, MemberRequestDto.MemberEditRequest editRequest);
    // 회원 삭제
    public void deleteMember(Long id);
}
