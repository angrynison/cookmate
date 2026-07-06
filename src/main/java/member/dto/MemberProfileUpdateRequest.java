package member.dto;

import global.type.Cuisine;
import member.domain.Member;

import java.util.Set;

public record MemberProfileUpdateRequest(

        String name,
        Member.Sex sex,
        Set<Cuisine> cuisines
) {
}
