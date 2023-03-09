package ru.astondevs.asber.apigateway.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

/**
 * Filter Util that returns only Jwt from header without Bearer.
 */
public class FilterUtil {
    private static final String BEARER = "Bearer ";

    /**
     * Method that gets Token from request.
     * @param request Request with all input parameters from request
     * @return {@link String} token
     */
    public static String getTokenFromRequest(ServerHttpRequest request) {
        final String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER)) {
            return authHeader.substring(7);
        }
        return null;
    }
}
