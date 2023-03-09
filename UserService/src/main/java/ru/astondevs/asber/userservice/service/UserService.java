package ru.astondevs.asber.userservice.service;

import ru.astondevs.asber.userservice.dto.RegisterClientDto;
import ru.astondevs.asber.userservice.dto.RegisterNotClientDto;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.Contacts;
import ru.astondevs.asber.userservice.entity.PassportData;
import ru.astondevs.asber.userservice.entity.UserProfile;
import ru.astondevs.asber.userservice.entity.enums.ClientStatus;

import java.util.Optional;
import java.util.UUID;

/**
 * Service that works with {@link ClientStatus}.
 */
public interface UserService {

    /**
     * Method that registers non-client user.
     *
     * @param registerNotClientDto from {@link RegisterNotClientDto}
     * @param client               from {@link Client}
     * @param contact              from {@link Contacts}
     * @param passportData         from {@link PassportData}
     */
    void registerNotClient(RegisterNotClientDto registerNotClientDto, Client client,
                           Contacts contact, PassportData passportData, UserProfile userProfile);

    /**
     * Method that registers client user.
     *
     * @param registerClientDto from {@link RegisterClientDto}
     */
    void registerClient(RegisterClientDto registerClientDto);

    /**
     * Method that gets user registration status by mobile phone.
     *
     * @return {@link Contacts}
     */
    Optional<Contacts> getUserRegistrationStatusByMobilePhone(String mobilePhone);

    /**
     * Method that gets client status.
     *
     * @param contact Credit id from {@link Contacts}
     * @return {@link ClientStatus}
     */
    ClientStatus getClientStatus(Contacts contact);

    /**
     * Method that gets client id.
     *
     * @param contact Credit id from {@link Contacts}
     */
    UUID getClientId(Contacts contact);
}
