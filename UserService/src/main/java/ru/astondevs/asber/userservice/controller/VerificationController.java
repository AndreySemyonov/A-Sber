package ru.astondevs.asber.userservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.userservice.dto.AccessAndRefreshTokensDto;
import ru.astondevs.asber.userservice.dto.MobilePhoneDto;
import ru.astondevs.asber.userservice.dto.MobilePhoneAndVerificationCodeDto;
import ru.astondevs.asber.userservice.dto.PassportNumberDto;
import ru.astondevs.asber.userservice.dto.PassportVerificationDto;
import ru.astondevs.asber.userservice.mapper.AuthenticationMapper;
import ru.astondevs.asber.userservice.service.AuthenticationService;
import ru.astondevs.asber.userservice.service.ContactsService;
import ru.astondevs.asber.userservice.service.PassportDataService;
import ru.astondevs.asber.userservice.service.VerificationService;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Controller that handles requests to {@link VerificationService}.
 */
@Api("Verification controller")
@Slf4j
@RestController
@RequestMapping("/api/v1/verifications")
@RequiredArgsConstructor
public class VerificationController {
    private final VerificationService verificationService;
    private final ContactsService contactsService;
    private final PassportDataService passportDataService;
    private final AuthenticationService authenticationService;
    private final AuthenticationMapper authenticationMapper;

    /**
     * End-point that verifies contact using {@link VerificationService#createOrUpdateVerification(String)}.
     *
     * @param mobilePhoneDto from request body
     */
    @PostMapping
    @ApiOperation(value = "Create or update verification", authorizations = {@Authorization("Authorization")})
    public void createOrUpdateVerification(@RequestBody @Valid MobilePhoneDto mobilePhoneDto) {
        log.info("Request for create/update verification for mobile number: {}", mobilePhoneDto.getMobilePhone());
        verificationService.createOrUpdateVerification(mobilePhoneDto.getMobilePhone());
        log.info("Verification created/updated for: {}", mobilePhoneDto.getMobilePhone());
    }

    /**
     * End-point that gets generate verification code by passport number.
     * using {@link ContactsService#getPhoneByPassportNumber(String)},
     * and {@link VerificationService#createOrUpdateVerification(String)}.
     * @param passportNumberDto from request body.
     */
    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Generate verification code by passport number")
    public void generateVerificationByPassportNumber(@RequestBody @Valid PassportNumberDto passportNumberDto) {
        log.info("Request for create/update verification for passport number: {}", passportNumberDto.getPassportNumber());
        verificationService.createOrUpdateVerificationByPassport(passportNumberDto.getPassportNumber());
        log.info("Verification created/updated for: {}", passportNumberDto.getPassportNumber());
    }

    /**
     * End-point that verifies code using {@link VerificationService#verifyCode(String, String)}.
     *
     * @param codeDto from request body
     */
    @PostMapping("/verify")
    @ApiOperation(value = "Verify code")
    public void verifyCode(@RequestBody @Valid MobilePhoneAndVerificationCodeDto codeDto) {
        log.info("Request for code verification for mobile number: {}", codeDto.getMobilePhone());
        verificationService.verifyCode(codeDto.getMobilePhone(), codeDto.getVerificationCode());
        log.info("Verified code for: {}", codeDto.getMobilePhone());
    }

    /**
     * End-point that verifies code using {@link VerificationService#verifyCode(String, String)}.
     * and create access and refresh tokens using {@link AuthenticationService}
     * @param passportVerificationDto from request body
     */
    @PostMapping("/verify/passport")
    @ApiOperation(value = "Verify code and get authorization tokens")
    public AccessAndRefreshTokensDto verifyCodeByPassportNumber(
            @RequestBody @Valid PassportVerificationDto passportVerificationDto) {
        log.info("Request for code verification for passport number: {}",  passportVerificationDto.getPassportNumber());
        verificationService.verifyCodeByPassport(passportVerificationDto.getPassportNumber(), passportVerificationDto.getVerificationCode());
        log.info("Verified code for: {}", passportVerificationDto.getPassportNumber());

        UUID clientId = passportDataService.getClientIdByPassportNumber(passportVerificationDto.getPassportNumber());
        AccessAndRefreshTokensDto accessAndRefreshTokensDto = authenticationMapper.toAccessAndRefreshTokensDto(
                authenticationService.generateAccessToken(clientId.toString()),
                authenticationService.updateAccessToken(clientId.toString()));
        log.info("Return access and refresh tokens for clientId: {}", clientId);

        return accessAndRefreshTokensDto;
    }

    /**
     * End-point that verifies code using {@link VerificationService#verifyCode(String, String)}.
     * and create access and refresh tokens using {@link AuthenticationService}
     * @param codeDto from request body
     */
    @PostMapping("/verify/phone")
    @ApiOperation(value = "Verify code and get authorization tokens")
    public AccessAndRefreshTokensDto verifyCodeByPhoneNumber(
            @RequestBody @Valid MobilePhoneAndVerificationCodeDto codeDto) {
        log.info("Request for code verification for mobile number: {}",  codeDto.getMobilePhone());
        verificationService.verifyCode(codeDto.getMobilePhone(), codeDto.getVerificationCode());
        log.info("Verified code for: {}", codeDto.getMobilePhone());

        UUID clientId = contactsService.getClientIdByPhoneNumber(codeDto.getMobilePhone());
        AccessAndRefreshTokensDto accessAndRefreshTokensDto = authenticationMapper.toAccessAndRefreshTokensDto(
                authenticationService.generateAccessToken(clientId.toString()),
                authenticationService.updateAccessToken(clientId.toString()));
        log.info("Return access and refresh tokens for clientId: {}", clientId);

        return accessAndRefreshTokensDto;
    }
}
