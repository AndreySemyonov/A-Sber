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
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.Contacts;
import ru.astondevs.asber.userservice.entity.UserProfile;
import ru.astondevs.asber.userservice.repository.UserProfileRepository;
import ru.astondevs.asber.userservice.service.impl.UserProfileServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserProfileServiceImplTest {
    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Mock
    private ContactsService contactsService;
    @Mock
    private UserProfileRepository userProfileRepository;

    private Client client;
    private UserProfile userProfile;
    private Contacts contacts;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(UUID.fromString("8fda6526-fcba-4f92-bfab-ada4e46acbd7"))
                .firstName("firstName")
                .lastName("lastName")
                .countryOfResidence("country")
                .build();
        contacts = Contacts.builder()
                .mobilePhone("123455")
                .client(client)
                .build();
        userProfile = UserProfile.builder()
                .client(client)
                .passwordEncoded("badpassword")
                .securityQuestion("question")
                .securityAnswer("answer")
                .build();
    }

    @DisplayName("If user found by mobile phone then we change password")
    @Test
    void changePasswordByClientId_shouldChangePassword() {
        when(userProfileRepository.findUserProfileByClientId(any())).thenReturn(Optional.of(userProfile));
        when(userProfileRepository.save(any())).thenReturn(userProfile);

        InOrder inOrder = Mockito.inOrder(contactsService, userProfileRepository);

        userProfileService.changePasswordByClientId(UUID.fromString("8fda6526-fcba-4f92-bfab-ada4e46acbd7"), "newpassword");

        inOrder.verify(userProfileRepository, times(1)).findUserProfileByClientId(any());
        inOrder.verify(userProfileRepository, times(1)).save(userProfile);
        assertEquals(userProfile.getPasswordEncoded(), "newpassword");
    }

    @DisplayName("If user found by clientId phone then we change password")
    @Test
    void changePassword_shouldChangePassword_ByClientId() {
        when(userProfileRepository.findUserProfileByClientId(any())).thenReturn(Optional.of(userProfile));
        when(userProfileRepository.save(any())).thenReturn(userProfile);

        userProfileService.changePassword(client.getId(), "badpassword", "newpassword");

        verify(userProfileRepository, times(1)).findUserProfileByClientId(any());
        verify(userProfileRepository, times(1)).save(userProfile);
    }

    @DisplayName("If user profile found by clientId then we change secret question and answer")
    @Test
    void changeSecretQuestionAndAnswer_shouldChangeSecretQuestionAndAnswer() {
        when(userProfileRepository.findUserProfileByClientId(any())).thenReturn(Optional.of(userProfile));
        when(userProfileRepository.save(any())).thenReturn(userProfile);

        userProfileService.changeSecretQuestionAndAnswer(UUID.fromString("8fda6526-fcba-4f92-bfab-ada4e46acbd7"),
                "my favourite movie", "untouchable");

        verify(userProfileRepository, times(1)).findUserProfileByClientId(any());
        verify(userProfileRepository, times(1)).save(userProfile);
        assertEquals(userProfile.getSecurityQuestion(), "my favourite movie");
        assertEquals(userProfile.getSecurityAnswer(), "untouchable");
    }

    @Test
    void save_shouldSaveUserProfile() {
        when(userProfileRepository.save(userProfile)).thenReturn(userProfile);
        userProfileService.save(userProfile);
        verify(userProfileRepository, times(1)).save(userProfile);
    }
}