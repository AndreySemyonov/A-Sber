package ru.astondevs.asber.userservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.userservice.dto.MobilePhoneDto;
import ru.astondevs.asber.userservice.dto.RegisterNotClientDto;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.Contacts;
import ru.astondevs.asber.userservice.entity.PassportData;
import ru.astondevs.asber.userservice.repository.ContactsRepository;
import ru.astondevs.asber.userservice.repository.PassportDataRepository;
import ru.astondevs.asber.userservice.service.ContactsService;
import ru.astondevs.asber.userservice.util.exception.EmailNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of {@link ContactsService}.
 * Works with {@link ContactsRepository}.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ContactsServiceImpl implements ContactsService {
    private final ContactsRepository contactsRepository;

    /**
     * {@inheritDoc}
     * For saving contact uses method {@link ContactsRepository#save(Object)}.
     *
     * @param notClientDto from {@link RegisterNotClientDto}
     * @param client       from {@link Client}
     * @param contact      from {@link Contacts}
     * @return {@link Contacts}
     */
    @Override
    @Transactional
    public Contacts save(RegisterNotClientDto notClientDto, Client client, Contacts contact) {
        log.info("Contact save: {}", contact.getId());
        contact.setClient(client);
        contact.setSmsNotificationEnabled(false);
        contact.setEmailSubscriptionEnabled(false);
        contact.setPushNotificationEnabled(false);

        log.info("Contact saved: {}", contact.getId());
        return contactsRepository.save(contact);
    }

    /**
     * {@inheritDoc}
     * For getting contact by phone from db using method {@link ContactsRepository#findContactsByMobilePhone(String)}.
     *
     * @return {@link Contacts}
     */
    @Override
    public Optional<Contacts> findContactsByMobilePhone(String mobilePhone) {
        log.info("Find contact by phone number: {}", mobilePhone);
        Optional<Contacts> contact = contactsRepository.findContactsByMobilePhone(mobilePhone);

        log.info("Contact found by number: {}", mobilePhone);
        return contact;
    }

    /**
     * {@inheritDoc}
     * For getting contact with verification by phone from db
     * using method {@link ContactsRepository#findContactsWithVerificationByMobilePhone(String)}.
     *
     * @return {@link Contacts}
     * @throws EntityNotFoundException if account not found
     */
    @Override
    public Contacts findContactsWithVerificationByMobilePhone(String mobilePhone) {
        log.info("Find contact with verification by phone: {}", mobilePhone);
        Contacts contact = contactsRepository.findContactsWithVerificationByMobilePhone(mobilePhone)
                .orElseThrow(() -> new EntityNotFoundException("not found contacts by mobile phone = " + mobilePhone));

        log.info("Found contact with verification by phone: {}", mobilePhone);
        return contact;
    }

    /**
     * {@inheritDoc}
     * For getting phone by passport number from db
     * using method {@link ContactsRepository#getPhoneByPassportNumber(String)}.
     *
     * @throws EntityNotFoundException if phone number not found
     */
    @Override
    public MobilePhoneDto getPhoneByPassportNumber(String passportNumber) {
        log.info("Getting phone by passport number: {}", passportNumber);
        String phoneNumber = contactsRepository.getPhoneByPassportNumber(passportNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "phone number wasn't found by passportNumber = " + passportNumber));

        log.info("Found phone by passport number: {}", passportNumber);
        return new MobilePhoneDto(phoneNumber);
    }

    /**
     * {@inheritDoc}
     * For changing email subscription uses method {@link ContactsRepository#save(Object)}.
     *
     * @throws EmailNotFoundException if email not found
     */
    @Override
    @Transactional
    public void changeEmailSubscriptionNotification(UUID uuid, Boolean notificationStatus) {
        log.info("Change email subscription notification for id: {}", uuid);
        Contacts contacts = contactsRepository.findContactsByClientId(uuid).orElseThrow(EntityNotFoundException::new);
        if (contacts.getEmail() != null) {
            contacts.setEmailSubscriptionEnabled(notificationStatus);
            contactsRepository.save(contacts);
            log.info("Changed email subscription notification for id: {}", uuid);
        } else {
            log.error("Email not found for id: {}", uuid);
            throw new EmailNotFoundException();
        }
    }

    /**
     * {@inheritDoc}
     * For changing email using method {@link ContactsRepository#save(Object)}.
     *
     * @throws EntityNotFoundException if contact not found
     */
    @Override
    @Transactional
    public void changeEmail(UUID clientId, String email) {
        log.info("Change email for id: {}", clientId);
        Contacts contactsForClientId = contactsRepository.findContactsByClientId(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Contacts not found for client with id = " + clientId));

        contactsForClientId.setEmail(email);
        contactsRepository.save(contactsForClientId);
        log.info("Email changed for id: {}", clientId);
    }

    /**
     * {@inheritDoc}
     * For changing SMS notificatoin status.
     *
     * @throws EntityNotFoundException if client not found
     */
    @Override
    @Transactional
    public void changeSmsNotificationStatus(UUID uuid, Boolean notificationStatus) {
        log.info("Change sms notification status for id: {}", uuid);
        contactsRepository.findContactsByClientId(uuid)
                .orElseThrow(() -> new EntityNotFoundException("not found client with id = " + uuid))
                .setSmsNotificationEnabled(notificationStatus);
        log.info("Changed sms notification status for id: {}", uuid);
    }

    /**
     * {@inheritDoc}
     * For changing push notification setting using method {@link ContactsRepository#save(Object)}.
     */
    @Override
    @Transactional
    public void changePushNotification(UUID uuid, Boolean notificationStatus) {
        log.info("Change push notification status for id: {}", uuid);
        Contacts contacts = contactsRepository.findContactsByClientId(uuid).orElseThrow(EntityNotFoundException::new);
        contacts.setPushNotificationEnabled(notificationStatus);
        contactsRepository.save(contacts);
        log.info("Changed push notification status for id: {}", uuid);
    }

    /**
     * {@inheritDoc}
     * For getting user notification propertiesusing method {@link ContactsRepository#findContactsByClientId(UUID)}.
     *
     * @return {@link Contacts}
     * @throws EntityNotFoundException if contact not found
     */
    @Override
    public Contacts getUserNotifications(UUID clientId) {
        log.info("Finding contact by id: {}", clientId);
        Contacts contactsForClientId = contactsRepository.findContactsByClientId(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Contacts not found for client with id = " + clientId));

        log.info("Found contact by id: {}", clientId);
        return contactsForClientId;
    }

    /**
     * {@inheritDoc}
     * For getting client id from db by phone number
     * uses method {@link #findContactsByMobilePhone(String)}.
     * @param phoneNumber phone number from {@link Contacts}.
     * @return {@link UUID}.
     * @throws EntityNotFoundException if client not found.
     */
    @Override
    public UUID getClientIdByPhoneNumber(String phoneNumber){
        return  findContactsByMobilePhone(phoneNumber)
                .map(contacts -> {
                    log.info("Returning client id with phone number: {}", contacts.getMobilePhone());
                    return contacts.getClient().getId();
                })
                .orElseThrow(() -> {
                    log.error("No client with phone number: {}", phoneNumber);
                    return new EntityNotFoundException();
                });
    }
}
