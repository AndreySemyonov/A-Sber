package ru.astondevs.asber.apigateway.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;
import ru.astondevs.asber.apigateway.util.JwtUtil;
import ru.astondevs.asber.apigateway.util.exception.InvalidJwtException;

import java.util.Arrays;
import java.util.List;

import static ru.astondevs.asber.apigateway.util.FilterUtil.getTokenFromRequest;

/**
 * Refresh Token Validation Filter that validates an authentication request by using refresh token.
 * Works with {@link JwtUtil} for validation Refresh Token.
 */
@RequiredArgsConstructor
public class RefreshTokenValidationFilter implements WebFilter {
    private final JwtUtil jwtUtil;
    private final PathPattern refreshPathPattern = new PathPatternParser().parse("/user/api/v1/login/token");
    private final PathPattern pinPathPattern = new PathPatternParser().parse("/user/api/v1/login/pin");
    private final List<PathPattern> pathPatternList = Arrays.asList(refreshPathPattern, pinPathPattern);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (pathPatternList.stream().anyMatch(pathPattern -> pathPattern.matches(exchange.getRequest().getPath()))) {
            String token = getTokenFromRequest(exchange.getRequest());

            if (token == null || !jwtUtil.validateRefreshToken(token)) {
                return Mono.error(new InvalidJwtException());
            }
        }
        return chain.filter(exchange);
    }

}
