package ru.astondevs.asber.userservice.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.astondevs.asber.userservice.dto.RegisterNotClientDto;
import ru.astondevs.asber.userservice.entity.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = {ClientMapperImpl.class})
public class ClientMapperTest {

    @Autowired
    private ClientMapper mapper;

    @Test
    public void RegisterNotClientDtoToClient_whenMaps_thenCorrect() {
        RegisterNotClientDto registerNotClientDto = RegisterNotClientDto.builder()
                .mobilePhone("01234567890")
                .password("Do_123_your-best")
                .securityQuestion("Who you are?")
                .securityAnswer("Me is me!")
                .firstName("Jackie")
                .middleName("Mikhailovich")
                .lastName("Chan")
                .passportNumber("1519889")
                .countryOfResidence("Russian Federation")
                .build();
        Client client = mapper.clientFromRegisterNotClientDto(registerNotClientDto);

        assertEquals(client.getFirstName(), registerNotClientDto.getFirstName());
        assertEquals(client.getLastName(), registerNotClientDto.getLastName());
        assertEquals(client.getSurName(), registerNotClientDto.getMiddleName());
        assertEquals(client.getCountryOfResidence(), registerNotClientDto.getCountryOfResidence());
    }

    @Test
    public void RegisterNotClientDtoToClient_whenSourceIsNull() {
        RegisterNotClientDto registerNotClientDto = null;
        Client client = mapper.clientFromRegisterNotClientDto(registerNotClientDto);
        assertNull(client);
    }
}