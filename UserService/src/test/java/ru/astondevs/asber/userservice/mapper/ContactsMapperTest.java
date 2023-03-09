package ru.astondevs.asber.userservice.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.astondevs.asber.userservice.dto.RegisterNotClientDto;
import ru.astondevs.asber.userservice.entity.Contacts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = {ContactsMapperImpl.class})
public class ContactsMapperTest {

    @Autowired
    private ContactsMapper mapper;

    private RegisterNotClientDto notClientDto;

    @BeforeAll
    public void setup() {
        notClientDto = RegisterNotClientDto.builder()
                .mobilePhone("89211234567")
                .password("password")
                .securityQuestion("is it my security question?")
                .securityAnswer("probably yes")
                .email("not_already_existing_email@gmail.com")
                .firstName("Ivan")
                .middleName("Ivanov")
                .lastName("Ivanovich")
                .passportNumber("2949123456")
                .countryOfResidence("Russia")
                .build();
    }

    @Test
    public void contactsFromRegisterNotClientDtoNotEmpty() {

        Contacts contact = mapper.contactsFromRegisterNotClientDto(notClientDto);
        assertEquals(contact.getMobilePhone(), notClientDto.getMobilePhone());
        assertEquals(contact.getEmail(), notClientDto.getEmail());
    }

    @Test
    public void contactsFromRegisterNotClientDtoEmpty() {

        Contacts contact = mapper.contactsFromRegisterNotClientDto(null);
        assertNull(contact);
    }
}
