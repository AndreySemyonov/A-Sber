package ru.astondevs.asber.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.astondevs.asber.apigateway.util.properties.SecurityProperties;

import javax.crypto.SecretKey;
import java.security.Key;

/**
 * Jwt Util that performs operations with JWT.
 */
@Slf4j
@Component
public class JwtUtil {
    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    /**
     * Class constructor specifying securityProperties.
     * @param securityProperties {@link SecurityProperties} that stores value for decoding SecretKey
     */
    public JwtUtil(SecurityProperties securityProperties) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityProperties.getAccessSecret()));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityProperties.getRefreshSecret()));
    }

    /**
     * Method that validates AccessToken.
     * @param accessToken AccessToken for subsequent requests
     * @return {@link Boolean} true if can parse claims Jws, otherwise false
     */
    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    /**
     * Method that validates RefreshToken.
     * @param refreshToken RefreshToken for updating a pair of access and refresh tokens
     * @return {@link Boolean} true if can parse claims Jws, otherwise false
     */
    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(String token, Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sigEx) {
            log.error("Invalid signature", sigEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    /**
     * Method that gets Claims.
     * @param token AccessToken
     * @return {@link Claims} information asserted about a subject
     */
    public Claims getAccessClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtAccessSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Method that gets clientId from token.
     * @param token AccessToken
     * @return {@link String} client id
     */
    public String getClientId(String token) {
        Claims accessClaims = this.getAccessClaims(token);
        return accessClaims.getSubject();
    }
}
