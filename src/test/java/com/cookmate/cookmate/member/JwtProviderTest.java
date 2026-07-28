package com.cookmate.cookmate.member;

import com.cookmate.global.security.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JwtProviderTest {

//    @MockitoBean
//    JwtProvider jwtProvider;
//
//    @Test
//    @DisplayName("토큰 발급,검증,여러가지 메소드 테스트")
//    void jwtProviderTest() {
//
//        Long id = 15L;
//        String role = "ADMIN";
//
//        String token = jwtProvider.createToken(id,role);
//        Boolean isValid = jwtProvider.validateToken(token);
//        Long tokenId = jwtProvider.getMemberIdFromToken(token);
//        String tokenRole = jwtProvider.getRoleFromToken(token);
//
//        assertThat(token).isNotNull();
//        assertThat(isValid).isTrue();
//        assertThat(tokenId).isEqualTo(id);
//        assertThat(tokenRole).isEqualTo(role);
//
//        System.out.println(token);
//    }




}
