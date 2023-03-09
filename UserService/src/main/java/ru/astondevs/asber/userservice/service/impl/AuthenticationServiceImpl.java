package ru.astondevs.asber.userservice.service.impl;

import io.jsonwebtoken.MalformedJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.astondevs.asber.userservice.dto.AccessAndRefreshTokensDto;
import ru.astondevs.asber.userservice.dto.FingerprintDto;
import ru.astondevs.asber.userservice.entity.Fingerprint;
import ru.astondevs.asber.userservice.entity.UserProfile;
import ru.astondevs.asber.userservice.mapper.AuthenticationMapper;
import ru.astondevs.asber.userservice.repository.FingerprintRepository;
import ru.astondevs.asber.userservice.service.AuthenticationService;
import ru.astondevs.asber.userservice.service.UserProfileService;
import ru.astondevs.asber.userservice.util.JwtProvider;
import ru.astondevs.asber.userservice.util.exception.InvalidFingerprintException;
import ru.astondevs.asber.userservice.util.exception.PasswordIncorrectException;
import ru.astondevs.asber.userservice.util.properties.SecurityProperties;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link AuthenticationService}.
 * Works with {@link FingerprintRepository}.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String BEARER = "Bearer ";
    private final UserProfileService userProfileService;
    private final FingerprintRepository fingerprintRepository;
    private final JwtProvider jwtProvider;
    private final StringRedisTemplate redisTemplate;
    private final SecurityProperties securityProperties;
    private final AuthenticationMapper authenticationMapper;

    /**
     * {@inheritDoc}
     * For refresh token.
     *
     * @throws MalformedJwtException if refresh token is invalid.
     */
    @Override
    public String refreshAccessToken(String authHeader) {
        log.info("Refreshing access token");
        String refreshToken = getTokenFromHeader(authHeader);
        String id = jwtProvider.getRefreshClaims(refreshToken).getSubject();
        String savedRefreshToken = redisTemplate.opsForValue().get(id);

        if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
            log.info("Refresh access token was generated");
            return jwtProvider.generateAccessToken(id);
        }
        log.error("Invalid refresh token");
        throw new MalformedJwtException("invalid refresh token");
    }

    /**
     * {@inheritDoc}
     * For login uses method {@link UserProfileService#findUserProfileByPhoneNumber(String)}.
     *
     * @return {@link AccessAndRefreshTokensDto}
     */
    @Override
    @Cacheable("authentication")
    public String loginByPhone(String phoneNumber, String password) {
        log.info("Login by phone number: {}", phoneNumber);
        UserProfile userProfile = userProfileService.findUserProfileByPhoneNumber(phoneNumber);
        checkPasswordEquality(userProfile, password);

        log.info("Logged in: {}", userProfile.getClient().getId());
        return userProfile.getClient().getId().toString();
    }

    /**
     * {@inheritDoc}
     * For login uses method {@link UserProfileService#findUserProfileByPassportNumber(String)}.
     *
     * @return {@link AccessAndRefreshTokensDto}
     */
    @Override
    @Cacheable("authentication")
    public String loginByPassport(String passportNumber, String password) {
        log.info("Login by passport number: {}", passportNumber);
        UserProfile userProfile = userProfileService.findUserProfileByPassportNumber(passportNumber);
        checkPasswordEquality(userProfile, password);

        log.info("Logged in by passport number: {}", userProfile.getClient().getId());
        return userProfile.getClient().getId().toString();
    }

    /**
     * {@inheritDoc}
     * For check password equality.
     *
     * @throws PasswordIncorrectException if account not found
     */
    private void checkPasswordEquality(UserProfile userProfile, String password) {
        log.info("Checking password equality for user: {}", userProfile.getClient().getId());
        if (!userProfile.getPasswordEncoded().equals(password)) {
            log.error("Password is incorrect for user: {}", userProfile.getClient().getId());
            throw new PasswordIncorrectException();
        }
        log.info("Password is correct for user: {}", userProfile.getClient().getId());
    }

    /**
     * {@inheritDoc}
     * For generating access token {@link JwtProvider#generateAccessToken(String)}.
     */
    public String generateAccessToken(String id) {
        log.info("Generating access token");
        String accessToken = jwtProvider.generateAccessToken(id);

        log.info("Access token was generated");
        return accessToken;
    }

    /**
     * {@inheritDoc}
     * For update access token {@link JwtProvider#generateRefreshToken(String)}.
     */
    public String updateAccessToken(String id) {
        log.info("Updating access token");
        final String refreshToken = jwtProvider.generateRefreshToken(id);

        redisTemplate.opsForValue().set(id, refreshToken,
                securityProperties.getRefreshTokenTimeoutInMinutes(), TimeUnit.MINUTES);

        log.info("Access token was updated");
        return refreshToken;
    }

    /**
     * {@inheritDoc}
     * For getting token from header.
     */
    private String getTokenFromHeader(String authHeader) {
        log.info("Getting access token from header");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER)) {
            log.info("Access token was received from header");
            return authHeader.substring(7);
        }
        log.info("No access token in header specified");
        return null;
    }

    /**
     * {@inheritDoc}
     * For saving fingerprint uses method {@link FingerprintRepository#save(Object)}.
     *
     * @param fingerprint from {@link Fingerprint}
     */
    @Override
    @Transactional
    public void saveFingerprint(Fingerprint fingerprint) {
        log.info("Saving fingerprint");
        fingerprintRepository.save(fingerprint);
        log.info("Fingerprint saved");
    }


    /**
     * {@inheritDoc}
     *
     * @param fingerprintDto {@link FingerprintDto}
     * @param authHeader     String from request header
     * @return {@link AccessAndRefreshTokensDto} with access token
     */
    @Override
    @Transactional
    public AccessAndRefreshTokensDto loginByPin(FingerprintDto fingerprintDto, String authHeader) {

        String refreshToken = getTokenFromHeader(authHeader);
        String clientId = jwtProvider.getRefreshClaims(refreshToken).getSubject();
        log.info("Request for saved refresh token in redis");
        String savedRefreshToken = redisTemplate.opsForValue().get(clientId);

        UserProfile userProfile = userProfileService.findUserProfileByClientId(UUID.fromString(clientId));
        log.info("Request for saved fingerprint in data base by client id: {}", clientId);
        Fingerprint fingerprint = userProfile.getClient().getFingerprint();



        if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
            if (fingerprint == null) {
                log.error("Fingerprint not found");
                throw new EntityNotFoundException("Fingerprint not found for client with id = " + clientId);
            } else if (fingerprint.getFingerprint().equals(fingerprintDto.getFingerprint())) {
                log.info("Returning access and refresh tokens");
                return authenticationMapper.toAccessAndRefreshTokensDto(
                        generateAccessToken(clientId.toString()),
                        updateAccessToken(clientId.toString()));
            } else {
                log.error("Fingerprint is invalid for clientId: {}", clientId);
                throw new InvalidFingerprintException("Invalid fingerprint for client with id = " + clientId);
            }}
        log.error("Invalid refresh token");
        throw new MalformedJwtException("Invalid refresh token");
    }
}
