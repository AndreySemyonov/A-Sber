package ru.astondevs.asber.userservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.userservice.dto.AccessAndRefreshTokensDto;
import ru.astondevs.asber.userservice.dto.AccessTokenDto;
import ru.astondevs.asber.userservice.dto.FingerprintDto;
import ru.astondevs.asber.userservice.dto.RequestLoginDto;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.Fingerprint;
import ru.astondevs.asber.userservice.mapper.AuthenticationMapper;
import ru.astondevs.asber.userservice.mapper.FingerprintMapper;
import ru.astondevs.asber.userservice.service.AuthenticationService;
import ru.astondevs.asber.userservice.service.ClientService;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Controller that handles requests to {@link AuthenticationService} and return response using {@link AuthenticationMapper}.
 */
@Api("User authentication controller")
@Slf4j
@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final AuthenticationMapper authenticationMapper;
    private final FingerprintMapper fingerprintMapper;
    private final ClientService clientService;

    /**
     * End-point that allow user to proceed login process using
     * {@link AuthenticationService#loginByPhone(String, String)},
     * {@link AuthenticationService#loginByPassport(String, String)}.
     *
     * @param requestLoginDto from request body
     * @return {@link AccessAndRefreshTokensDto} with access token
     */
    @PostMapping
    @ApiOperation(value = "Login user")
    public AccessAndRefreshTokensDto login(@Valid @RequestBody RequestLoginDto requestLoginDto) {
        log.info("Request login for user: {}", requestLoginDto.getLogin());

        String clientId = switch (requestLoginDto.getType()) {
            case PHONE_NUMBER -> (
                    authenticationService.loginByPhone(requestLoginDto.getLogin(), requestLoginDto.getPassword()));
            case PASSPORT_NUMBER -> (
                    authenticationService.loginByPassport(requestLoginDto.getLogin(), requestLoginDto.getPassword()));
        };

        AccessAndRefreshTokensDto accessAndRefreshTokensDto = authenticationMapper.toAccessAndRefreshTokensDto(
                authenticationService.generateAccessToken(clientId),
                authenticationService.updateAccessToken(clientId));

        log.info("Return access and refresh tokens for clientId: {}", clientId);

        return accessAndRefreshTokensDto;
    }


    /**
     * End-point that proceed login operation by pin using {@link AuthenticationService#loginByPin(UUID, FingerprintDto, String)}.
     *
     * @param clientId client id from {@link Client}
     * @param fingerprintDto {@link FingerprintDto}
     * @param authHeader String from request header
     * @return {@link AccessAndRefreshTokensDto} with access token
     */
    @PostMapping("/pin")
    @ApiOperation(value = "Login user by PIN")
    public AccessAndRefreshTokensDto login(@RequestBody @Valid FingerprintDto fingerprintDto,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        AccessAndRefreshTokensDto accessAndRefreshTokensDto = authenticationService
                .loginByPin(fingerprintDto, authHeader);
        return accessAndRefreshTokensDto;
    }

    /**
     * End-point that gets refreshed token using {@link AuthenticationService#refreshAccessToken(String)}.
     * After this converting string value of accessToken to list of accessToken response dto,
     * using {@link AuthenticationMapper#toAccessTokenDto(String)}.
     *
     * @param authHeader String from request header
     * @return {@link AccessTokenDto} with access token
     */
    @GetMapping("/token")
    @ApiOperation(value = "Refresh access token")
    public AccessTokenDto refreshAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        log.info("Request for refresh accessToken");
        AccessTokenDto accessTokenDto = authenticationMapper
                .toAccessTokenDto(authenticationService.refreshAccessToken(authHeader));
        log.info("Returning AccessTokenDto");
        return accessTokenDto;
    }


    /**
     * End-point that saves fingerprint using {@link AuthenticationService#saveFingerprint(Fingerprint)}.
     *
     * @param clientId client id from {@link Client}
     * @param fingerprintDto {@link FingerprintDto}
     */
    @PostMapping("/fingerprint")
    @ApiOperation(value = "Save fingerprint")
    public void saveFingerprint(@RequestParam UUID clientId, @RequestBody @Valid FingerprintDto fingerprintDto) {
        log.info("Request for save fingerprint for clientId: {}", clientId);

        Client client = clientService.getClientWithFingerprint(clientId);
        Fingerprint fingerprint = client.getFingerprint();

        if (fingerprint != null) {
            fingerprint.setFingerprint(fingerprintDto.getFingerprint());
        } else {
            fingerprint = fingerprintMapper.fingerprintFromFingerprintDto(fingerprintDto);
            fingerprint.setClient(client);
        }

        authenticationService.saveFingerprint(fingerprint);
        log.info("Saved fingerprint for clientId: {}", clientId);
    }
}
