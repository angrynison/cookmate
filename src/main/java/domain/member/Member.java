package domain.member;

import global.type.Cuisine;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false, length = 100)
    String name;
    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cuisine cuisine;
    
    public enum Sex {
        남,
        녀
    }
}
