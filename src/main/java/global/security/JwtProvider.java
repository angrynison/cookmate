package global.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final Long expirationTime;

    public JwtProvider(@Value("${jwt.key}") String key, @Value("${jwt.expiration_time}") Long expirationTime) {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes());
        this.expirationTime = expirationTime;
    }

    // 토큰 생성
    public String createToken(Long memberId, String role) {
        // LocalDateTime을 쓰지 않는 이유는?
        Date now = new Date();
        Date expDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                // member 식별자 주제
                .subject(String.valueOf(memberId))
                // claim: 추가로 담고 싶은 데이터를 Map형태로 넣기
                .claim("role",role)
                .issuedAt(now)
                .expiration(expDate)
                .signWith(secretKey)
                .compact();
    }

    // 토큰 검증 (회원 ID)
    public Long getMemberIdFromToken(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.parseLong(claims.getSubject());
    }

    // 토큰 검증 (role)

    // 토큰 유효성 검증 @param token 프론트 엔드가 보낸 jwt 문자열을 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT Signature");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Signature");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Signature");
        } catch (IllegalArgumentException e) {
            log.error("JWT Token is Wrong");
        }
        return false;
    }
}
