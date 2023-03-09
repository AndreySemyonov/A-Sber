package ru.astondevs.asber.apigateway.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import ru.astondevs.asber.apigateway.util.JwtUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Modify Request Gateway Filter Factory that creates custom filters for adding client id as queryParam.
 */
@Slf4j
@Component
public class ModifyRequestGatewayFilterFactory extends
        AbstractGatewayFilterFactory<ModifyRequestGatewayFilterFactory.Config> {

    private final JwtUtil jwtUtil;

    private final PathPattern refreshPathPattern = new PathPatternParser().parse("/api/v1/login/token");
    private final PathPattern pinPathPattern = new PathPatternParser().parse("/api/v1/login/pin");
    private final List<PathPattern> pathPatternList = Arrays.asList(refreshPathPattern, pinPathPattern);

    /**
     * Class constructor specifying jwtUtil.
     * @param jwtUtil that uses for getting client id
     */
    public ModifyRequestGatewayFilterFactory(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    /**
     * Method that adds client id as queryParam.
     * @param config Static class for customizing filter
     * @return GatewayFilter custom filter
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String headerAuth = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (Objects.nonNull(headerAuth) &&
                    pathPatternList.stream().noneMatch(pathPattern -> pathPattern.matches(exchange.getRequest().getPath()))) {
                String token = headerAuth.substring(7);
                log.info("Authorization header: {}", token);

                String clientId = jwtUtil.getClientId(token);
                log.info("Id for authorized client: {}", clientId);

                ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(originalRequest -> originalRequest.uri(
                                UriComponentsBuilder.fromUri(exchange.getRequest().getURI())
                                        .replaceQueryParam("clientId", clientId)
                                        .build().toUri()))
                        .build();
                log.info("Added request param for authorized client: {}", modifiedExchange.getRequest().getURI());
                return chain.filter(modifiedExchange);
            }
            return chain.filter(exchange);
        };
    }

    /**
     * Static class for adding message in application.yml.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Config {
        private String baseMessage;
    }
}
