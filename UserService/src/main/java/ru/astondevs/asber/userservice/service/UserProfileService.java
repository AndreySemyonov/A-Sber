package ru.astondevs.asber.userservice.service;

import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.UserProfile;

import java.util.UUID;

/**
 * Service that works with {@link UserProfile}.
 */
public interface UserProfileService {
    /**
     * Method that saves user profile.
     *
     * @param userProfile from {@link UserProfile}
     * @param client      from {@link Client}
     * @return {@link UserProfile}
     */
    UserProfile save(UserProfile userProfile, Client client);

    /**
     * Method that saves user profile by user profile.
     *
     * @param userProfile from {@link UserProfile}
     * @return {@link UserProfile}
     */
    UserProfile save(UserProfile userProfile);

    /**
     * Method that changes user password.
     */
    void changePassword(UUID clientId, String oldPassword, String newPassword);

    /**
     * Method that changes secret question and answer.
     */
    void changeSecretQuestionAndAnswer(UUID uuid, String securityQuestion, String securityAnswer);

    /**
     * Method that gets user profile by passport number.
     *
     * @return {@link UserProfile}
     */
    UserProfile findUserProfileByPassportNumber(String passportNumber);

    /**
     * Method that gets user profile by phone number.
     *
     * @return {@link UserProfile}
     */
    UserProfile findUserProfileByPhoneNumber(String phoneNumber);

    /**
     * Method that gets user profile by client id.
     *
     * @return {@link UserProfile}
     */
    UserProfile findUserProfileByClientId(UUID clientId);

    /**
     * Method that reset user password.
     */
    void changePasswordByClientId(UUID clientId, String newPassword);
}
