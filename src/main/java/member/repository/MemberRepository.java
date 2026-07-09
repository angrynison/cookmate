package member.repository;

import member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 중복 ID 검사기
    boolean existsByName(String name);
    boolean existsByLoginId(String loginId);
    Optional<Member> findByName(String name);
    Optional<Member> findByLoginId(String email);
}
