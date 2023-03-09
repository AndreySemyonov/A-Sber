package ru.astondevs.asber.apigateway.webclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.astondevs.asber.apigateway.dto.AccessAndRefreshTokenDto;
import ru.astondevs.asber.apigateway.dto.AccessTokenDto;
import ru.astondevs.asber.apigateway.dto.LoginRequestDto;

/**
 * Feign Client that handles requests to UserService.
 */
@FeignClient(value = "user-service")
public interface AuthenticationClient {
    /**
     * Method that gets AccessToken and RefreshToken from UserService.
     * @param dto {@link LoginRequestDto} that sends into UserService
     * @return {@link AccessAndRefreshTokenDto} that receives from UserService
     */
    @PostMapping("/api/v1/login")
    AccessAndRefreshTokenDto login(@RequestBody LoginRequestDto dto);

    /**
     * Method that gets AccessToken from UserService.
     * @return {@link AccessTokenDto} that receives from UserService
     */
    @GetMapping("/api/v1/login/token")
    AccessTokenDto refreshAccessToken();
}
