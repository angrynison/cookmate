package member.service.Impl;

import global.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import member.domain.Member;
import member.dto.JoinRequest;
import member.dto.LoginRequest;
import member.dto.JwtResponse;
import member.MemberRepository;
import member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor // 불변 변수 자동주입
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // 회원가입
    @Override
    @Transactional
    public Long join(JoinRequest joinRequest) {

        // 회원가입 요청 Null 검사
        if (joinRequest == null || joinRequest.loginId() == null || joinRequest.password() == null) {
            throw new IllegalArgumentException("form is empty");
        }

        // 로그인 ID 중복 검사
        if (memberRepository.existsByLoginId(joinRequest.loginId())) {
            throw new IllegalArgumentException("loginId is already in use");
        }

        // 비밀번호 암호화 (사용자가 입력한 비밀번호를 알아볼 수 없게 변경)
        String encodedPassword = passwordEncoder.encode(joinRequest.password());

        Member newMember = Member.builder()
                .loginId(joinRequest.loginId())
                .password(encodedPassword)
                .build();

        memberRepository.save(newMember);

        return newMember.getId();
    }

    // 로그인
    @Override
    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {

        // 프론트가 보낸 아이디로 DB에서 회원 조회
        Member member = memberRepository.findByLoginId(loginRequest.loginId())
                .orElseThrow(() -> new IllegalArgumentException("loginId not found"));


        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
            throw new IllegalArgumentException("password not match");
        }

        // 로그인이 성공했다면 토큰 발급
        String accessToken = jwtProvider.createToken(member.getId(), "USER");


        return new JwtResponse(accessToken);
    }


}
