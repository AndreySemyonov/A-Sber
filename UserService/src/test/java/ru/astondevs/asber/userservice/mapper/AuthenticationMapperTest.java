package ru.astondevs.asber.userservice.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.astondevs.asber.userservice.dto.AccessAndRefreshTokensDto;
import ru.astondevs.asber.userservice.dto.AccessTokenDto;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = {AuthenticationMapperImpl.class})
public class AuthenticationMapperTest {

    @Autowired
    private AuthenticationMapper mapper;

    private final String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI5YzA0MWFiNy1jMzNiLTQ2YmEtYTM5Mi03ZTVhOGQ5ZTNkZGQiLCJleHAiOjE2NzE2MTA0MTZ9.ozhnYlwtLFYOpHGFNtReqzSrasnDjZtXxRhNc39h1qzX6SpfSpeB_FGPoR1k4o4w";
    private final String refresToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI5YzA0MWFiNy1jMzNiLTQ2YmEtYTM5Mi03ZTVhOGQ5ZTNkZGQiLCJleHAiOjE2NzE2MTA0MTZ9.ozhnYlwtLFYOpHGFNtReqzSrasnDjZtXxRhNc39h1qzX6SpfSpeB_FGPoR1k4o3f";
    private final UUID uuid = UUID.randomUUID();//.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2");

    @Test
    public void accessTokenToAccessTokenDtoNotEmpty(){
        AccessTokenDto accessTokenDto = mapper.toAccessTokenDto(accessToken);

        assertEquals(accessTokenDto.getAccessToken(), accessToken);
    }

    @Test
    public void accessTokenToAccessTokenDtoEmpty(){
        AccessTokenDto accessTokenDto = mapper.toAccessTokenDto(null);

        assertNull(accessTokenDto);
    }

    @Test
    public void accessTokenToAccessAndRefreshTokensDtoNotEmpty() {
        AccessAndRefreshTokensDto accessAndRefreshTokensDto = mapper.toAccessAndRefreshTokensDto(accessToken, refresToken);

        assertEquals(accessAndRefreshTokensDto.getAccessToken(), accessToken);
        assertEquals(accessAndRefreshTokensDto.getRefreshToken(), refresToken);
    }

    @Test
    public void accessTokenToAccessAndRefreshTokensDtoEmpty() {
        AccessAndRefreshTokensDto accessAndRefreshTokensDto = mapper.toAccessAndRefreshTokensDto(null, null);

        assertNull(accessAndRefreshTokensDto);
    }

    @Test
    public void accessTokenToAccessAndRefreshTokensDtoAccessTokenEmpty() {
        AccessAndRefreshTokensDto accessAndRefreshTokensDto = mapper.toAccessAndRefreshTokensDto(null, refresToken);

        assertNull(accessAndRefreshTokensDto.getAccessToken());
        assertEquals(accessAndRefreshTokensDto.getRefreshToken(), refresToken);
    }

    @Test
    public void accessTokenToAccessAndRefreshTokensDtoRefreshTokenEmpty() {
        AccessAndRefreshTokensDto accessAndRefreshTokensDto = mapper.toAccessAndRefreshTokensDto(accessToken, null);

        assertEquals(accessAndRefreshTokensDto.getAccessToken(), accessToken);
        assertNull(accessAndRefreshTokensDto.getRefreshToken());
    }


}
