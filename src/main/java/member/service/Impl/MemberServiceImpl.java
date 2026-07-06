package member.service.Impl;

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

    @Override
    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {






        return null;
    }


}
