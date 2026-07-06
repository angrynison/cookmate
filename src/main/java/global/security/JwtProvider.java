package global.security;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final SecretKey secretKey;

    public JwtProvider(@Value("${jwt.key}") String key) {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }






}
