package ru.astondevs.asber.userservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.userservice.dto.RegisterClientDto;
import ru.astondevs.asber.userservice.dto.RegisterNotClientDto;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.enums.ClientStatus;
import ru.astondevs.asber.userservice.entity.Contacts;
import ru.astondevs.asber.userservice.entity.PassportData;
import ru.astondevs.asber.userservice.entity.UserProfile;
import ru.astondevs.asber.userservice.mapper.UserStatusMapper;
import ru.astondevs.asber.userservice.service.impl.UserServiceImpl;
import ru.astondevs.asber.userservice.util.exception.UserExistsException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserStatusMapper userStatusMapper;

    @Mock
    private ClientService clientService;
    @Mock
    private ContactsService contactsService;
    @Mock
    private UserProfileService userProfileService;
    @Mock
    private PassportDataService passportDataService;
    @Mock
    private DepositServiceClient depositServiceClient;
    @Mock
    private CreditServiceClient creditServiceClient;

    private RegisterNotClientDto registerNotClientDto;
    private RegisterClientDto registerClientDto;
    private Client clientFromRegisterNotClientDto;
    private UserProfile userProfileFromRegisterNotClientDto;
    private Contacts contactsFromRegisterNotClientDto;
    private PassportData passportDataFromRegisterNotClientDto;
    private Client clientForGetUserStatus;
    private Contacts contactForGetUserStatus;
    private Client clientWithoutContacts;


    @BeforeEach
    void setUp() {
        registerNotClientDto = RegisterNotClientDto.builder()
                .mobilePhone("89111234567")
                .password("password")
                .securityQuestion("is it my security question?")
                .securityAnswer("probably yes")
                .email("some_user@gmail.com")
                .firstName("Ivan")
                .middleName("Ivanov")
                .lastName("Ivanovich")
                .passportNumber("2949123456")
                .countryOfResidence("Russia")
                .build();

        registerClientDto = RegisterClientDto.builder()
                .id(UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2"))
                .mobilePhone("89111234567")
                .password("password")
                .securityQuestion("is it my security question?")
                .securityAnswer("probably yes")
                .email("some_user@gmail.com")
                .build();

        clientFromRegisterNotClientDto = Client.builder()
                .firstName(registerNotClientDto.getFirstName())
                .lastName(registerNotClientDto.getLastName())
                .surName(registerNotClientDto.getMiddleName())
                .countryOfResidence(registerNotClientDto.getCountryOfResidence())
                .dateAccession(LocalDateTime.now())
                .clientStatus(ClientStatus.NOT_ACTIVE)
                .build();

        userProfileFromRegisterNotClientDto = UserProfile.builder()
                .client(clientFromRegisterNotClientDto)
                .passwordEncoded(registerNotClientDto.getPassword())
                .securityQuestion(registerNotClientDto.getSecurityQuestion())
                .securityAnswer(registerNotClientDto.getSecurityAnswer())
                .dateAppRegistration(LocalDateTime.now())
                .build();

        contactsFromRegisterNotClientDto = Contacts.builder()
                .smsNotificationEnabled(false)
                .pushNotificationEnabled(false)
                .emailSubscriptionEnabled(false)
                .email(registerNotClientDto.getEmail())
                .mobilePhone(registerNotClientDto.getMobilePhone())
                .client(clientFromRegisterNotClientDto)
                .build();

        clientFromRegisterNotClientDto.setUserProfile(userProfileFromRegisterNotClientDto);
        clientFromRegisterNotClientDto.setContacts(contactsFromRegisterNotClientDto);

        passportDataFromRegisterNotClientDto = PassportData.builder()
                .identificationPassportNumber(registerNotClientDto.getPassportNumber())
                .build();

        clientForGetUserStatus = Client.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .surName("Ivanovich")
                .countryOfResidence("russia")
                .dateAccession( LocalDateTime.of(LocalDate.of(2022, 10, 13),
                        LocalTime.of(21, 36, 15, 0)))
                .build();

        contactForGetUserStatus = Contacts.builder()
                .client(clientForGetUserStatus)
                .smsNotificationEnabled(false)
                .pushNotificationEnabled(false)
                .emailSubscriptionEnabled(false)
                .email("some_user@gmail.com")
                .mobilePhone("89111234567")
                .build();

        clientWithoutContacts = Client.builder()
                .id(UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2"))
                .contacts(null)
                .build();
    }

    @DisplayName("If all data is valid and client is not already registered then we register not client")
    @Test
    void registerNotClient_shouldRegisterNotClient() {

        when(contactsService.save(registerNotClientDto, clientFromRegisterNotClientDto, contactsFromRegisterNotClientDto))
                .thenReturn(contactsFromRegisterNotClientDto);
        when(userProfileService.save(userProfileFromRegisterNotClientDto, clientFromRegisterNotClientDto)).thenReturn(userProfileFromRegisterNotClientDto);
        when(passportDataService.save(passportDataFromRegisterNotClientDto, clientFromRegisterNotClientDto)).thenReturn(passportDataFromRegisterNotClientDto);

        InOrder inOrder = Mockito.inOrder(clientService, contactsService, userProfileService, passportDataService);

        userService.registerNotClient(registerNotClientDto, clientFromRegisterNotClientDto, contactsFromRegisterNotClientDto, passportDataFromRegisterNotClientDto, userProfileFromRegisterNotClientDto);

        inOrder.verify(contactsService, times(1)).save(registerNotClientDto, clientFromRegisterNotClientDto, contactsFromRegisterNotClientDto);
        inOrder.verify(userProfileService, times(1)).save(userProfileFromRegisterNotClientDto, clientFromRegisterNotClientDto);
        inOrder.verify(passportDataService, times(1)).save(passportDataFromRegisterNotClientDto, clientFromRegisterNotClientDto);
    }

    @DisplayName("If all data is valid and client is already registered then we add contacts and userProfile data")
    @Test
    void registerClient_shouldRegisterClient() {
        when(clientService.getClientWithContactsAndUserProfile(registerClientDto.getId())).thenReturn(clientFromRegisterNotClientDto);

        userService.registerClient(registerClientDto);
    }

    @Test
    void getUserStatusRegistrationBy_ForNotRegistered() {

        clientForGetUserStatus.setClientStatus(ClientStatus.NOT_REGISTERED);
        when(contactsService.findContactsByMobilePhone(contactForGetUserStatus.getMobilePhone()))
                .thenReturn(Optional.of(contactForGetUserStatus));

        userService.getUserRegistrationStatusByMobilePhone(contactForGetUserStatus.getMobilePhone());

        verify(contactsService).findContactsByMobilePhone(contactForGetUserStatus.getMobilePhone());
    }

    @Test
    void getUserStatusRegistrationBy_ForNotActiveUser() {

        clientForGetUserStatus.setClientStatus(ClientStatus.NOT_ACTIVE);
        when(contactsService.findContactsByMobilePhone(contactForGetUserStatus.getMobilePhone())).thenThrow(new UserExistsException());

        try {
            userService.getUserRegistrationStatusByMobilePhone(contactForGetUserStatus.getMobilePhone());
        } catch (UserExistsException e) {
            assertEquals("User already registered", e.getMessage ());
        }

        verify(contactsService).findContactsByMobilePhone(contactForGetUserStatus.getMobilePhone());
    }

    @Test
    void getUserStatusRegistrationBy_ForActiveUser() {

        clientForGetUserStatus.setClientStatus(ClientStatus.ACTIVE);
        when(contactsService.findContactsByMobilePhone(contactForGetUserStatus.getMobilePhone())).thenThrow(new UserExistsException());

        try {
            userService.getUserRegistrationStatusByMobilePhone(contactForGetUserStatus.getMobilePhone());
        } catch (UserExistsException e) {
            assertEquals("User already registered", e.getMessage());
        }

        verify(contactsService).findContactsByMobilePhone(contactForGetUserStatus.getMobilePhone());
    }

    @Test
    void registerClient_shouldSetClientsEmailSubscriptionToFalse() {
        when(clientService.getClientWithContactsAndUserProfile(registerClientDto.getId())).thenReturn(clientWithoutContacts);
        userService.registerClient(registerClientDto);
        assertFalse(clientWithoutContacts.getContacts().isEmailSubscriptionEnabled());
    }
}