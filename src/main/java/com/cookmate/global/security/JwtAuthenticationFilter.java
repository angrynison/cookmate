package com.cookmate.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// api 요청 -> 토큰 검증 클래스
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = resolveToken(request);

        // 토큰이 Null값이 아니고 && 유효한 토큰이면 (변조,만료,지원하지않는)
        if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {

            Long memberId = jwtProvider.getMemberIdFromToken(token);
            String role = jwtProvider.getRoleFromToken(token);

            // Spring Security가 이해할 수 있는 Authentication 객체 생성 (세션 인증 토큰 생성)
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    memberId,
                    null,
                    List.of(new SimpleGrantedAuthority(role))
            );
            
            // 서버 내부의 보안 context에 인증 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Controller에서 쓰기 편하도록 HttpServletRequest 속성에도 memberId 추가
            request.setAttribute("memberId", memberId);

        }

        System.out.println("Success");
        // 다음필터에 보내거나, Controller에게 요청을 보내거나
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {

        // 토큰 가져오기
        String bearerToken = request.getHeader("Authorization");

        // 헤더에 토큰 값이 있고 && Bearer로 시작하는지
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {

            // Bearer 뒤에 있는 진짜 토큰 부분만 잘라오기
            return bearerToken.substring(7);
        }
        return null;
    }
}
