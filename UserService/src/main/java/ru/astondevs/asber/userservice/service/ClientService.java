package ru.astondevs.asber.userservice.service;

import ru.astondevs.asber.userservice.entity.Client;

import java.util.UUID;

/**
 * Service that works with {@link Client}.
 */
public interface ClientService {
    /**
     * Method that saves non-client user.
     *
     * @param client Credit id from {@link Client}
     * @return {@link Client}
     */
    Client save(Client client);

    /**
     * Method that gets client with contact and user profile.
     *
     * @return {@link Client}
     */
    Client getClientWithContactsAndUserProfile(UUID id);

    /**
     * Method that gets client with fingerprint.
     *
     * @return {@link Client}
     */
    Client getClientWithFingerprint(UUID id);
}
