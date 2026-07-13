package com.cookmate.member;

import com.cookmate.member.dto.MemberRequestDto;
import com.cookmate.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import com.cookmate.member.service.MemberService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<Long> signup(@RequestBody MemberRequestDto.JoinRequest joinRequest) {
        Long memberId = memberService.join(joinRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberId);
    }

    // 로그인
    @PostMapping("/user/login")
    public ResponseEntity<MemberResponseDto.JwtTokenResponse> login(@RequestBody MemberRequestDto.LoginRequest loginRequest) {
         return ResponseEntity.ok(memberService.login(loginRequest));
    }

    // 회원 프로필 생성
    @PostMapping("/user/profile")
    public ResponseEntity<Long> profileRegister(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MemberRequestDto.ProfileRequest profileRequest) {
        Long id = Long.parseLong(userDetails.getUsername());
        Long memberId = memberService.createProfile(id, profileRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberId);
    }

    // 회원 정보 수정
    @PatchMapping("/user")
    public ResponseEntity<Long> updateMember(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MemberRequestDto.EditRequest editRequest) {
        Long id = Long.parseLong(userDetails.getUsername());
        Long memberId = memberService.updateMember(id, editRequest);
        return ResponseEntity.ok(memberId);
    }

    // 회원 탈퇴
    @DeleteMapping("/user")
    public void deleteMember(@AuthenticationPrincipal UserDetails userDetails) {
        Long id = Long.parseLong(userDetails.getUsername());
        memberService.deleteMember(id);
    }
    
    // 회원정보 반환
    @GetMapping("/user/info")
    public ResponseEntity<MemberResponseDto.MemberProfileResponse> getMemberInfo(@AuthenticationPrincipal UserDetails userDetails) {
        Long id = Long.parseLong(userDetails.getUsername());
        MemberResponseDto.MemberProfileResponse profile = memberService.getMyProfile(id);
        return ResponseEntity.ok(profile);
    }

    // 관리자 등록
    @PostMapping ("/admin")
    ResponseEntity<Long> adminSignup(@RequestBody MemberRequestDto.JoinRequest joinRequest) {
        Long adminId = memberService.join(joinRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminId);
    }

    // 관리자 로그인
    @PostMapping("/admin/login")
    ResponseEntity<MemberResponseDto.JwtTokenResponse> adminLogin(@RequestBody MemberRequestDto.LoginRequest loginRequest) {
        MemberResponseDto.JwtTokenResponse jwtTokenResponse = memberService.login(loginRequest);
        return jwtTokenResponse != null ? ResponseEntity.ok(jwtTokenResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
