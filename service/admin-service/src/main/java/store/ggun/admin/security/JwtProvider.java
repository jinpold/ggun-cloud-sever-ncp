package store.ggun.admin.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import store.ggun.admin.domain.dto.AdminDto;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Log4j2
@Component
public class JwtProvider {
    Instant expiredDate = Instant.now().plus(1, ChronoUnit.DAYS);
    Instant refreshExpiredDate = Instant.now().plus(7, ChronoUnit.DAYS);

    @Value("${jwt.iss}")
    private String issuer;
    private final SecretKey secretKey;


    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    public String createToken(AdminDto adminDto) {

        //Access Token
        String accessToken = Jwts.builder()
                .issuer(issuer)
                .signWith(secretKey)
                .expiration(Date.from(expiredDate))
                .claim("sub", "turing")
                .claim("username", adminDto.getUsername())
                .claim("role", adminDto.getRole())  // 관리자(ad), 소비자 (role)
                .claim("adminId", adminDto.getId())
                .compact();

        log.info("로그인 성공으로 발급된 토큰 : " + accessToken);
        return accessToken;

    }

    public String refreshAccessToken(String refreshToken) {
        Claims claims = getPayload(refreshToken);
        Instant newExpiredDate = Instant.now().plus(1, ChronoUnit.DAYS);

        return Jwts.builder()
                .issuer(issuer)
                .signWith(secretKey)
                .expiration(Date.from(newExpiredDate))
                .claim("sub", claims.getSubject())
                .claim("username", claims.get("username"))
                .claim("role", claims.get("role"))
                .claim("adminId", claims.get("adminId"))
                .compact();
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        log.info("프론트에서 넘어온 리퀘스트 값 : {}", request.getServletPath());
        String bearerToken = request.getHeader("Authorization");
        log.info("프론트에서 넘어온 토큰 값 : {}", bearerToken);
        return bearerToken != null && bearerToken.startsWith("Bearer ") ?
                bearerToken.substring(7): "undefined";

    }

    public void printPayload(String accessToken) {
        String[] chunks = accessToken.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        log.info("accessToken Header :" +header);
        log.info("accessToken payload :" +payload);
    }

    public Claims getPayload(String token){
        try {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build().parseSignedClaims(token).getPayload();
        } catch (Exception exception) {
            throw new RuntimeException("Failed to parse JWT token", exception);
        }
    }
}
