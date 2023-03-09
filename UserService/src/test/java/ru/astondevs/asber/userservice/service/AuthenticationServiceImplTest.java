package ru.astondevs.asber.userservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.Fingerprint;
import ru.astondevs.asber.userservice.repository.FingerprintRepository;
import ru.astondevs.asber.userservice.service.impl.AuthenticationServiceImpl;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private FingerprintRepository fingerprintRepository;

    private Fingerprint fingerprint;
    private Client client;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(UUID.fromString("798060ff-ed9b-4745-857c-a7a2f4a2e3a2"))
                .firstName("firstName")
                .lastName("lastName")
                .countryOfResidence("country")
                .build();

        fingerprint = Fingerprint.builder()
                .client(client)
                .fingerprint("666666")
                .build();

    }

    @DisplayName("If client with uuid exists then we should save fingerprint")
    @Test
    void saveFingerprint_shouldSaveFingerprint() {
        when(fingerprintRepository.save(any())).thenReturn(fingerprint);

        authenticationService.saveFingerprint(fingerprint);

        verify(fingerprintRepository, times(1)).save(fingerprint);
    }
}
