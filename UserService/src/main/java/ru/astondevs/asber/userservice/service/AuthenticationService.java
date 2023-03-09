package ru.astondevs.asber.userservice.service;

import ru.astondevs.asber.userservice.dto.AccessAndRefreshTokensDto;
import ru.astondevs.asber.userservice.dto.FingerprintDto;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.Fingerprint;

/**
 * Service that works with {@link Fingerprint}, {@link Client}.
 */
public interface AuthenticationService {
    /**
     * Method that proceeds login by phone number.
     *
     * @return {@link AccessAndRefreshTokensDto}
     */
    String loginByPhone(String login, String password);

    /**
     * Method that proceeds login by passport number.
     *
     * @return {@link AccessAndRefreshTokensDto}
     */
    String loginByPassport(String login, String password);

    /**
     * Method that refreshes access token.
     *
     * @return {@link String}
     */
    String refreshAccessToken(String refreshToken);

    /**
     * Method that saves fingerprint.
     *
     * @param fingerprint fingerprint from {@link Fingerprint}
     */
    void saveFingerprint(Fingerprint fingerprint);


    /**
     * Method proceeds login by pin.
     *
     * @param fingerprintDto {@link FingerprintDto}
     * @param authHeader String from request header
     * @return {@link AccessAndRefreshTokensDto} with access token
     */
    AccessAndRefreshTokensDto loginByPin(FingerprintDto fingerprintDto, String authHeader);

    /**
     * Method generates access token.
     */
    String generateAccessToken(String id);

    /**
     * Method updates access token.
     */
    String updateAccessToken(String id);
}
