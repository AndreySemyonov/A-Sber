package ru.astondevs.asber.userservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.userservice.dto.UserNotificationsDto;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.Contacts;
import ru.astondevs.asber.userservice.mapper.ContactsMapper;
import ru.astondevs.asber.userservice.repository.ContactsRepository;
import ru.astondevs.asber.userservice.service.impl.ContactsServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ContactsServiceImplTest {
    @InjectMocks
    private ContactsServiceImpl contactsService;

    @Mock
    private ContactsRepository contactsRepository;

    @Mock
    private ContactsMapper contactsMapper;

    private Contacts contacts;
    private Client client;
    private UserNotificationsDto userNotificationsDto;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(UUID.fromString("0b8cd72e-4ee3-11ed-bdc3-0242ac120002"))
                .firstName("firstName")
                .lastName("lastName")
                .countryOfResidence("country")
                .build();
        contacts = Contacts.builder()
                .email("some_email@gmail.ru")
                .client(client)
                .build();
        userNotificationsDto = UserNotificationsDto.builder()
                .email("some_email@gmail.ru")
                .smsNotification(false)
                .pushNotification(false)
                .emailSubscription(false)
                .build();
    }

    @Test
    void changeEmailSubscriptionNotification_shouldChangeEmailSubscriptionNotification() {
        when(contactsRepository.findContactsByClientId(any())).thenReturn(Optional.of(contacts));
        when(contactsRepository.save(any())).thenReturn(contacts);

        contactsService.changeEmailSubscriptionNotification(contacts.getClient().getId(), true);

        verify(contactsRepository, times(1)).findContactsByClientId(any());
        verify(contactsRepository, times(1)).save(contacts);
        assertEquals(contacts.isEmailSubscriptionEnabled(), true);
    }

    @DisplayName("If user found by clientId his contacts then we are changing email")
    @Test
    void changeUserEmail_shouldChangeUserEmail() {
        when(contactsRepository.findContactsByClientId(any())).thenReturn(Optional.of(contacts));
        when(contactsRepository.save(any())).thenReturn(contacts);

        contactsService.changeEmail(client.getId(), "another_email@gmail.ru");

        verify(contactsRepository).findContactsByClientId(client.getId());
        verify(contactsRepository).save(contacts);
        assertEquals(contacts.getEmail(), "another_email@gmail.ru");
    }

    @DisplayName("If user found by clientId his contacts then we are changing email")
    @Test
    void getUserNotifications_shouldUserNotificationsDto() {
        when(contactsRepository.findContactsByClientId(client.getId())).thenReturn(Optional.of(contacts));
        when(contactsMapper.toUserNotificationsDto(isA(Contacts.class))).thenReturn(userNotificationsDto);

        UserNotificationsDto userNotifications = contactsMapper.toUserNotificationsDto(contactsService.getUserNotifications(client.getId()));

        verify(contactsRepository).findContactsByClientId(client.getId());
        assertEquals(contacts.getEmail(), userNotifications.getEmail());
        assertEquals(contacts.isSmsNotificationEnabled(), userNotifications.isSmsNotification());
        assertEquals(contacts.isPushNotificationEnabled(), userNotifications.isPushNotification());
        assertEquals(contacts.isEmailSubscriptionEnabled(), userNotifications.isEmailSubscription());
    }

    @Test
    void changePushNotification_shouldChangePushNotification() {
        when(contactsRepository.findContactsByClientId(any())).thenReturn(Optional.of(contacts));
        when(contactsRepository.save(any())).thenReturn(contacts);

        contactsService.changePushNotification(contacts.getClient().getId(), true);

        verify(contactsRepository, times(1)).findContactsByClientId(any());
        verify(contactsRepository, times(1)).save(contacts);
        assertEquals(contacts.isPushNotificationEnabled(), true);
    }
}

