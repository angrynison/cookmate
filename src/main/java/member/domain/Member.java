package member.domain;

import global.type.Cuisine;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 100)
    String name;
    @Column(nullable = false, unique = true)
    String loginId;
    @Column(nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    // 음식 선호도 다중 값을 저장하기 위한 memebr - memebr_cuisine의 many to one 매핑 테이블 생성
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "member_cuisine",
            joinColumns = @JoinColumn(name = "member_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "cuisine_type")
    private Set<Cuisine> cuisines = new HashSet<>();



    public enum Sex {
        남,
        녀
    }

}
