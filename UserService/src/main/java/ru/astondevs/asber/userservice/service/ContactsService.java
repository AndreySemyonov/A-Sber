package ru.astondevs.asber.userservice.service;

import ru.astondevs.asber.userservice.dto.MobilePhoneDto;
import ru.astondevs.asber.userservice.dto.RegisterNotClientDto;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.Contacts;
import ru.astondevs.asber.userservice.entity.PassportData;

import java.util.Optional;
import java.util.UUID;

/**
 * Service that works with {@link Contacts}.
 */
public interface ContactsService {
    /**
     * Method that creates non-client user.
     *
     * @param notClientDto RegisterNotClientDto from {@link RegisterNotClientDto}
     * @param client       from {@link Client}
     * @param contact      from {@link Contacts}
     * @return {@link Contacts}
     */
    Contacts save(RegisterNotClientDto notClientDto, Client client, Contacts contact);

    /**
     * Method that gets contact by mobile phone.
     *
     * @return {@link Contacts}
     */
    Optional<Contacts> findContactsByMobilePhone(String mobilePhone);

    /**
     * Method that gets contact with verification by mobile phone.
     *
     * @return {@link Contacts}
     */
    Contacts findContactsWithVerificationByMobilePhone(String mobilePhone);

    /**
     * Method that gets phone number by passport number.
     */
    MobilePhoneDto getPhoneByPassportNumber(String passportNumber);

    /**
     * Method that changes email subscription property.
     */
    void changeEmailSubscriptionNotification(UUID uuid, Boolean notificationStatus);

    /**
     * Method that changes email.
     */
    void changeEmail(UUID clientId, String email);

    /**
     * Method that changes sms notification status.
     */
    void changeSmsNotificationStatus(UUID uuid, Boolean notificationStatus);

    /**
     * Method that changes push-notification property.
     */
    void changePushNotification(UUID uuid, Boolean notificationStatus);

    /**
     * Method that gets user notifications properties.
     *
     * @return {@link Contacts}
     */
    Contacts getUserNotifications(UUID clientId);

    /**
     * For getting client id from db by phone number
     *
     * @param phoneNumber phone number from {@link Contacts}.
     * @return {@link UUID}.
     */
    UUID getClientIdByPhoneNumber(String phoneNumber);
}
