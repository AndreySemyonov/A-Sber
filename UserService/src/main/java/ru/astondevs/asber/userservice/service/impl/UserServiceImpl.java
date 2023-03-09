package ru.astondevs.asber.userservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.userservice.dto.RegisterClientDto;
import ru.astondevs.asber.userservice.dto.RegisterNotClientDto;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.PassportData;
import ru.astondevs.asber.userservice.entity.enums.ClientStatus;
import ru.astondevs.asber.userservice.entity.Contacts;
import ru.astondevs.asber.userservice.entity.UserProfile;
import ru.astondevs.asber.userservice.service.ClientService;
import ru.astondevs.asber.userservice.service.ContactsService;
import ru.astondevs.asber.userservice.service.CreditServiceClient;
import ru.astondevs.asber.userservice.service.DepositServiceClient;
import ru.astondevs.asber.userservice.service.PassportDataService;
import ru.astondevs.asber.userservice.service.UserProfileService;
import ru.astondevs.asber.userservice.service.UserService;
import ru.astondevs.asber.userservice.util.exception.UserExistsException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of {@link UserService}.
 * Works with {@link ClientService}, {@link ContactsService}, {@link UserProfileService}, {@link PassportDataService}.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final ClientService clientService;
    private final ContactsService contactsService;
    private final UserProfileService userProfileService;
    private final PassportDataService passportDataService;
    private final DepositServiceClient depositServiceClient;
    private final CreditServiceClient creditServiceClient;

    /**
     * {@inheritDoc}
     * For registering non-client user {@link ClientService#save(Client)}.
     *
     * @param notClientDto from {@link RegisterNotClientDto}
     * @param client       from {@link Client}
     * @param contact      from {@link Contacts}
     * @param passportData from {@link PassportData}
     */
    @Override
    @Transactional
    public void registerNotClient(RegisterNotClientDto notClientDto, Client client, Contacts contact,
                                  PassportData passportData, UserProfile userProfile) {
        log.info("Saving data for non-client user with phone number: {}", notClientDto.getMobilePhone());
        Optional<Contacts> optionalContacts = contactsService.findContactsByMobilePhone(notClientDto.getMobilePhone());
        if (optionalContacts.isPresent()){
            log.warn("Client with this phone number is already exists");
            throw new UserExistsException();
        } else {
            clientService.save(client);
            contactsService.save(notClientDto, client, contact);
            userProfileService.save(userProfile, client);
            passportDataService.save(passportData, client);
            log.info("Saved data for non-client user with phone number: {}", notClientDto.getMobilePhone());
        }
    }

    /**
     * {@inheritDoc}
     * For registering client user using {@link ClientService#getClientWithContactsAndUserProfile(UUID)}.
     *
     * @param clientDto from {@link RegisterClientDto}
     */
    @Override
    @Transactional
    public void registerClient(RegisterClientDto clientDto) {
        log.info("Saving data for client user with email: {}", clientDto.getEmail());
        Client client = clientService.getClientWithContactsAndUserProfile(clientDto.getId());
        client.setDateAccession(LocalDateTime.now());

        client.setClientStatus(hasActiveAccounts(clientDto));

        Contacts contacts = getContactsForClient(client);
        if (clientDto.getEmail() != null) {
            contacts.setEmail(clientDto.getEmail());
        }
        contacts.setMobilePhone(clientDto.getMobilePhone());

        UserProfile userProfile = getUserProfileForClient(client);
        userProfile.setPasswordEncoded(clientDto.getPassword());
        userProfile.setSecurityAnswer(clientDto.getSecurityAnswer());
        userProfile.setSecurityQuestion(clientDto.getSecurityQuestion());

        log.info("Saved data for client user with email: {}", clientDto.getEmail());
    }

    /**
     * {@inheritDoc}
     * For getting user profile by client.
     *
     * @param client from {@link Client}
     * @return {@link UUID}
     */
    private UserProfile getUserProfileForClient(Client client) {
        log.info("Getting user profile for client: {}", client.getId());
        if (client.getUserProfile() == null) {
            client.setUserProfile(new UserProfile());
        }
        UserProfile userProfile = client.getUserProfile();

        log.info("Return user profile for client: {}", client.getId());
        return userProfile;
    }

    /**
     * {@inheritDoc}
     * For getting contact for client.
     *
     * @param client from {@link Client}
     * @return {@link Contacts}
     */
    private Contacts getContactsForClient(Client client) {
        log.info("Getting contact for client: {}", client.getId());
        if (client.getContacts() == null) {
            client.setContacts(Contacts.builder()
                    .smsNotificationEnabled(false)
                    .pushNotificationEnabled(false)
                    .emailSubscriptionEnabled(false)
                    .build());
        }

        log.info("Return contact for client: {}", client.getId());
        return client.getContacts();
    }

    /**
     * {@inheritDoc}
     * For getting contact by phone number from db uses method {@link ContactsService#findContactsByMobilePhone(String)}.
     *
     * @return {@link Contacts}
     */
    @Override
    public Optional<Contacts> getUserRegistrationStatusByMobilePhone(String mobilePhone) {
        log.info("Getting user registration status by mobile phone: {}", mobilePhone);
        Optional<Contacts> contacts = contactsService.findContactsByMobilePhone(mobilePhone);
        log.info("Return user registration status by mobile phone: {}", mobilePhone);
        return contacts;
    }

    /**
     * {@inheritDoc}
     * For getting client status.
     *
     * @param userContact from {@link Contacts}
     * @return {@link ClientStatus}
     * @throws UserExistsException if user exists
     */
    public ClientStatus getClientStatus(Contacts userContact) {
        log.info("Getting client status");
        ClientStatus clientStatus;

        if (userContact == null) {
            log.info("User contatc is empty");
            clientStatus = ClientStatus.NOT_CLIENT;
        } else {
            clientStatus = userContact.getClient().getClientStatus();
        }

        log.info("Return client status");
        return clientStatus;
    }

    /**
     * {@inheritDoc}
     * For getting client id.
     *
     * @param userContact from {@link Contacts}
     */
    public UUID getClientId(Contacts userContact) {
        log.info("Getting client status");
        if (userContact == null) {
            log.info("Client id is NULL");
            return null;
        }

        log.info("Return client id for contact: {}", userContact.getId());
        return userContact.getClient().getId();
    }

    private ClientStatus hasActiveAccounts(RegisterClientDto dto) {
        if (depositServiceClient.getDepositsCount(dto.getId()) == 0 && creditServiceClient.getCreditsCount(dto.getId()) == 0) {
            return ClientStatus.NOT_ACTIVE;
        } else {
            return ClientStatus.ACTIVE;
        }
    }
}
