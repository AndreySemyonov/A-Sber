package ru.astondevs.asber.userservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.astondevs.asber.userservice.util.properties.SecurityProperties;

import javax.crypto.SecretKey;
import java.time.ZoneId;
import java.util.Date;

import static java.time.LocalDateTime.now;

/**
 * JwtProwider for security properties
 */
@Slf4j
@Component
public class JwtProvider {
    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;
    private final SecurityProperties securityProperties;

    /**
     * Class constructor initialising properties
     */
    public JwtProvider(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityProperties.getAccessSecret()));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityProperties.getRefreshSecret()));
    }

    /**
     * Method that generates access token
     *
     * @param subject {@link String}
     */
    public String generateAccessToken(String subject) {
        Date tokenTimeOut = Date.from(now().plusMinutes(securityProperties.getAccessTokenTimeoutInMinutes())
                .atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(tokenTimeOut)
                .signWith(jwtAccessSecret)
                .compact();
    }

    /**
     * Method that generates refresh token
     *
     * @param subject {@link String}
     */
    public String generateRefreshToken(String subject) {
        Date tokenTimeOut = Date.from(now().plusMinutes(securityProperties.getRefreshTokenTimeoutInMinutes())
                .atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(tokenTimeOut)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    /**
     * Method that gets refresh claims
     *
     * @param token {@link String}
     * @return {@link Claims}
     */
    public Claims getRefreshClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtRefreshSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

