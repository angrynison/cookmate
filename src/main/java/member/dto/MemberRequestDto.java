package member.dto;

import global.type.Cuisine;
import member.domain.Member;

import java.util.Set;

public class MemberRequestDto {

    private MemberRequestDto() {}

    // 회원가입 dto
    public record JoinRequest(
            String loginId,
            String password,
            String name
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
            Long id,
            Member.Sex sex,
            Set<Cuisine> cuisines
    ) {
    }

    // 회원 정보 수정 dto
    public record MemberEditRequest(
            Long id,
            String loginId,
            String password,
            String name
    ){
    }
}
