package ru.astondevs.asber.userservice.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.astondevs.asber.userservice.dto.UserStatusRegistrationDto;

/**
 * Enum used in {@link UserStatusRegistrationDto}.
 */
@Getter
@AllArgsConstructor
public enum ClientStatus {

    ACTIVE(true),
    NOT_ACTIVE(true),
    NOT_REGISTERED(false),
    NOT_CLIENT(false);

    private final boolean isRegistered;
}
