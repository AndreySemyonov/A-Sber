package ru.astondevs.asber.userservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.UserProfile;
import ru.astondevs.asber.userservice.repository.UserProfileRepository;
import ru.astondevs.asber.userservice.service.ContactsService;
import ru.astondevs.asber.userservice.service.UserProfileService;
import ru.astondevs.asber.userservice.util.exception.PasswordIncorrectException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of {@link UserProfileService}.
 * Works with {@link UserProfileRepository} and {@link ContactsService}.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final ContactsService contactsService;

    /**
     * {@inheritDoc}
     * For saving user profile using method {@link UserProfileRepository#save(Object)}.
     *
     * @param userProfile from {@link UserProfile}
     * @param client      from {@link Client}
     * @return {@link UserProfile}
     */
    @Override
    @Transactional
    public UserProfile save(UserProfile userProfile, Client client) {
        log.info("Saving userprofile for id: {}", client.getId());
        userProfile.setDateAppRegistration(LocalDateTime.now());
        userProfile.setClient(client);

        log.info("Saved userprofile for id: {}", client.getId());
        return userProfileRepository.save(userProfile);
    }

    /**
     * {@inheritDoc}
     * For saving user profile using method {@link UserProfileRepository#save(Object)}.
     *
     * @param userProfile from {@link UserProfile}
     * @return {@link UserProfile}
     */
    @Override
    public UserProfile save(UserProfile userProfile) {
        log.info("Saving userprofile for id: {}", userProfile.getId());
        UserProfile userProfileForSave = userProfileRepository.save(userProfile);

        log.info("Saved userprofile for id: {}", userProfile.getId());
        return userProfileForSave;
    }

    /**
     * {@inheritDoc}
     * For changing password using method {@link UserProfileRepository#save(Object)}.
     *
     * @throws EntityNotFoundException if user profile not found
     */
    @Override
    @Transactional
    public void changePassword(UUID clientId, String oldPassword, String newPassword) {
        log.info("Changing password for clientId: {}", clientId);
        UserProfile userProfile = userProfileRepository.findUserProfileByClientId(clientId)
                .orElseThrow(() -> new EntityNotFoundException("User profile not found for client with id = " + clientId));

        if (userProfile.getPasswordEncoded().isEmpty()
                || !userProfile.getPasswordEncoded().equals(oldPassword)) {
            log.error("Incorrect password for clientId: {}", clientId);
            throw new PasswordIncorrectException();
        }
        userProfile.setPasswordEncoded(newPassword);
        userProfileRepository.save(userProfile);
        log.info("Changed password for clientId: {}", clientId);
    }

    /**
     * {@inheritDoc}
     * For changing secret question and answer using method {@link UserProfileRepository#save(Object)}.
     */
    @Override
    @Transactional
    public void changeSecretQuestionAndAnswer(UUID uuid, String securityQuestion, String securityAnswer) {
        log.info("Changing secret password for id: {}", uuid);
        UserProfile userProfile = userProfileRepository.findUserProfileByClientId(uuid).orElseThrow(() -> new EntityNotFoundException("User profile not found with id" + uuid));
        userProfile.setSecurityQuestion(securityQuestion);
        userProfile.setSecurityAnswer(securityAnswer);
        userProfileRepository.save(userProfile);
        log.info("Changed secret password for id: {}", uuid);
    }

    /**
     * {@inheritDoc}
     * For getting user profile by phone number using method {@link UserProfileRepository#findUserProfileByPhoneNumber(String)}.
     *
     * @return {@link UserProfile}
     * @throws EntityNotFoundException if user profile not found
     */
    @Override
    public UserProfile findUserProfileByPhoneNumber(String phoneNumber) {
        log.info("Finding user profile by phone number: {}", phoneNumber);
        UserProfile userProfile = userProfileRepository
                .findUserProfileByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("User profile not found with phone number " + phoneNumber));

        log.info("Found user profile by phone number: {}", phoneNumber);
        return userProfile;
    }

    /**
     * {@inheritDoc}
     * For getting user profile by client id using method {@link UserProfileRepository#findUserProfileByClientId(UUID)}.
     *
     * @return {@link UserProfile}
     * @throws EntityNotFoundException if user profile not found
     */
    @Override
    public UserProfile findUserProfileByClientId(UUID clientId) {
        log.info("Finding user profile by clientId: {}", clientId);
        UserProfile userProfile = userProfileRepository.findUserProfileByClientId(clientId)
                .orElseThrow(() -> new EntityNotFoundException("User profile not found for client with id = " + clientId));

        log.info("Found user profile by clientId: {}", clientId);
        return userProfile;
    }

    /**
     * {@inheritDoc}
     * For getting user profile by passport number using method {@link UserProfileRepository#findUserProfileByClientId(UUID)}.
     *
     * @return {@link UserProfile}
     * @throws EntityNotFoundException if user profile not found
     */
    @Override
    public UserProfile findUserProfileByPassportNumber(String passportNumber) {
        log.info("Finding user profile by passport number: {}", passportNumber);
        UserProfile userProfile = userProfileRepository
                .findUserProfileByPassportNumber(passportNumber)
                .orElseThrow(() -> new EntityNotFoundException("User profile not found for passport number " + passportNumber));

        log.info("Found user profile by passport number: {}", passportNumber);
        return userProfile;
    }

    /**
     * {@inheritDoc}
     * For changing password using method {@link UserProfileRepository#save(Object)}.
     *
     * @throws EntityNotFoundException if user profile not found
     */
    @Transactional
    @Override
    public void changePasswordByClientId(UUID clientId, String newPassword) {
        log.info("Changing password for client id: {}", clientId);
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findUserProfileByClientId(clientId);
        if (optionalUserProfile.isPresent()) {
            UserProfile userProfile = optionalUserProfile.get();
            userProfile.setPasswordEncoded(newPassword);
            userProfileRepository.save(userProfile);
            log.info("Changed password for client id: {}", clientId);
        } else {
            log.error("User profile wasn't found for clientId: {}", clientId);
            throw new EntityNotFoundException("UserProfile not found");
        }
    }
}
