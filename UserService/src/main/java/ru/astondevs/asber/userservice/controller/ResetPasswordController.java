package ru.astondevs.asber.userservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.userservice.dto.PasswordDto;
import ru.astondevs.asber.userservice.service.UserProfileService;
import ru.astondevs.asber.userservice.entity.Client;

import javax.validation.Valid;
import java.util.UUID;
/**
 * Controller that handles requests to {@link UserProfileService}.
 *
 */
@Api("Reset password controller")
@Slf4j
@RestController
@RequestMapping("/api/v1/reset")
@RequiredArgsConstructor
public class ResetPasswordController {
    private final UserProfileService userProfileService;

    /**
     * End-point that updates user password
     * @param clientId client id from {@link Client}
     * @param passwordDto {@link PasswordDto}
     */
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Change password", authorizations = {@Authorization("Authorization")})
    public void changePasswordByClientId(@RequestParam UUID clientId, @RequestBody @Valid PasswordDto passwordDto) {
        log.info("Request for password change");
        userProfileService.changePasswordByClientId(clientId, passwordDto.getNewPassword());
        log.info("Password changed for user with clientId: {}", clientId);
    }
}
