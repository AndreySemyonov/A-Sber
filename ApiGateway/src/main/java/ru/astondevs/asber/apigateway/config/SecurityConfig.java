package ru.astondevs.asber.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;
import ru.astondevs.asber.apigateway.filter.JwtAccessTokenAuthenticationFilter;
import ru.astondevs.asber.apigateway.filter.RefreshTokenValidationFilter;
import ru.astondevs.asber.apigateway.util.JwtUtil;

/**
 * Security config that ensures access to our microservices only authenticated users,
 * excepted end-point for login and registration.
 */
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http, JwtUtil jwtUtil) {
        return http
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) ->
                        Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
                ).accessDeniedHandler((swe, e) ->
                        Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))
                )
                .and()
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(it ->
                        it
                                .pathMatchers("/user/api/v1/login/**", "/user/api/v1/verifications/**", "/user/api/v1/user/**",
                                        "/swagger-ui/**", "/configuration/ui", "/swagger-resources/**",
                                        "/configuration/**", "/webjars/**",  "/*/v3/api-docs").permitAll()
                                .pathMatchers("/**").authenticated()
                )
                .addFilterAt(new JwtAccessTokenAuthenticationFilter(jwtUtil), SecurityWebFiltersOrder.HTTP_BASIC)
                .addFilterAt(new RefreshTokenValidationFilter(jwtUtil), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PATCH");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
