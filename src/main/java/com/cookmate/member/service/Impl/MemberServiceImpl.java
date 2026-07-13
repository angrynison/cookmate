package com.cookmate.member.service.Impl;

import com.cookmate.global.security.JwtProvider;
import com.cookmate.global.type.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.cookmate.member.domain.Member;
import com.cookmate.member.dto.MemberRequestDto;
import com.cookmate.member.dto.MemberResponseDto;
import com.cookmate.member.repository.MemberRepository;
import com.cookmate.member.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.cookmate.pantry.repository.PantryRepository;

@Service
@RequiredArgsConstructor // 불변 변수 자동주입
public class MemberServiceImpl implements MemberService {

    private final PantryRepository pantryRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Value("${jwt.adminToken}")
    private String adminToken;


    // 회원가입
    @Override
    @Transactional
    public Long join(MemberRequestDto.JoinRequest joinRequest) {

        // 회원가입 요청 Null 검사
        if (joinRequest == null || joinRequest.loginId() == null || joinRequest.password() == null || joinRequest.name() == null) {
            throw new IllegalArgumentException("form is empty");
        }

        // 로그인 ID 중복 검사
        if (memberRepository.existsByLoginId(joinRequest.loginId())) {
            throw new IllegalArgumentException("loginId is already in use");
        }

        if (memberRepository.existsByName(joinRequest.name())) {
            throw new IllegalArgumentException("name is already in use");
        }

        Role role = Role.USER;
        if (joinRequest.isAdmin()) {
            if (!joinRequest.adminToken().equals(adminToken)) {
                throw new IllegalArgumentException("adminToken is wrong");
            }
            role = Role.ADMIN;
        }

        // 비밀번호 암호화 (사용자가 입력한 비밀번호를 알아볼 수 없게 변경)
        String encodedPassword = passwordEncoder.encode(joinRequest.password());

        Member newMember = Member.builder()
                .name(joinRequest.name())
                .loginId(joinRequest.loginId())
                .password(encodedPassword)
                .role(role)
                .build();

        memberRepository.save(newMember);

        return newMember.getId();
    }

    // 로그인
    @Override
    @Transactional
    public MemberResponseDto.JwtTokenResponse login(MemberRequestDto.LoginRequest loginRequest) {

        // 프론트가 보낸 아이디로 DB에서 회원 조회
        Member member = memberRepository.findByLoginId(loginRequest.loginId())
                .orElseThrow(() -> new IllegalArgumentException("loginId not found"));


        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
            throw new IllegalArgumentException("password not match");
        }

        // 로그인이 성공했다면 토큰 발급
        String accessToken = jwtProvider.createToken(member.getId(), String.valueOf(member.getRole()));

        return new MemberResponseDto.JwtTokenResponse(accessToken);
    }

    // 일단 보류
    @Override
    public MemberResponseDto.JwtTokenResponse logout() {
        return null;
    }


    // 회원 정보 조회
    @Override
    public MemberResponseDto.MemberProfileResponse getMyProfile(Long id) {
        Member profileMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        return MemberResponseDto.MemberProfileResponse.from(profileMember);
    }

    // 프로필 등록
    @Override
    public Long createProfile(Long id, MemberRequestDto.ProfileRequest profileRequest) {

        Member profileMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member could not found"));

        if (profileRequest.sex() == null) {
            throw new IllegalArgumentException("SEX must not be null");
        }

        profileMember.createProfile(profileMember.getSex(), profileMember.getCuisines());

        return profileMember.getId();
    }

    // 회원 탈퇴
    @Override
    public void deleteMember(Long id) {
        // 성능 면에서 on delete Cascade가 아닌 pantryRepository JPA를 사용
        pantryRepository.deleteById(id);
        memberRepository.deleteById(id);
    }

    // 회원정보 수정
    @Override
    @Transactional
    public Long updateMember(Long id, MemberRequestDto.EditRequest editRequest) {

        if (!passwordEncoder.matches(editRequest.password(), editRequest.password())) {
            throw new IllegalArgumentException("password not match");
        }

        Member editMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("member not found"));

        editMember.update(editRequest.name(), editRequest.loginId(), editRequest.password());

        return editMember.getId();
    }
}
