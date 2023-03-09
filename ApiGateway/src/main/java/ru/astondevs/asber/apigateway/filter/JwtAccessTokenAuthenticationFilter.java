package ru.astondevs.asber.apigateway.filter;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import ru.astondevs.asber.apigateway.util.JwtUtil;

import static ru.astondevs.asber.apigateway.util.FilterUtil.getTokenFromRequest;

/**
 * Jwt Access Token Authentication Filter that validates an authentication request.
 * Works with {@link JwtUtil} for validation Access Token.
 */
@RequiredArgsConstructor
public class JwtAccessTokenAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        // check whether client wants to login or refresh access token
        String token = getTokenFromRequest(exchange.getRequest());
        if (token != null && jwtUtil.validateAccessToken(token)) {
            final Claims claims = jwtUtil.getAccessClaims(token);

            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(
                    new UsernamePasswordAuthenticationToken(claims.getSubject(), null, null))
            );
        }
        return chain.filter(exchange);
    }
}
