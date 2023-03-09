package ru.astondevs.asber.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.userservice.dto.AccessAndRefreshTokensDto;
import ru.astondevs.asber.userservice.dto.AccessTokenDto;

/**
 * Mapper that converts tokens to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthenticationMapper {

    /**
     * Method that converts access token to access token dto.
     *
     * @return {@link AccessTokenDto}
     */
    AccessTokenDto toAccessTokenDto(String accessToken);

    /**
     * Method that converts access token and refreshed access token to access and refresh token dto.
     *
     * @return {@link AccessTokenDto}
     */
    AccessAndRefreshTokensDto toAccessAndRefreshTokensDto(
            String accessToken, String refreshToken
    );
}
