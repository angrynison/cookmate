package com.cookmate.member.domain;

import com.cookmate.global.type.Cuisine;
import com.cookmate.global.type.Role;
import com.cookmate.pantry.domain.Pantry;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @OneToMany(mappedBy = "member")
    List<Pantry> pantries = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

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


    // 최초 프로필 등록
    public void createProfile(Sex sex, Set<Cuisine> cuisines) {
        this.sex = sex;
        this.cuisines.addAll(cuisines);
    }

    // 회원 정보 수정
    public void update(
            String name,
            String loginId,
            String password
    ) {
        if (name != null) {
            this.name = name;
        }
        if (loginId != null) {
            this.loginId = loginId;
        }
        if (password != null) {
            this.password = password;
        }
    }



}
