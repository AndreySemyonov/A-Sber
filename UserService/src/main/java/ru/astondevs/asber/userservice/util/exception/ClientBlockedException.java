package ru.astondevs.asber.userservice.util.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.astondevs.asber.userservice.entity.Verification;
import ru.astondevs.asber.userservice.util.VerificationValidator;

/**
 * Class that throws exception if method {@link VerificationValidator#checkVerificationBlocked(Verification)},
 * {@link VerificationValidator#checkVerificationCode(Verification, String)},
 * {@link VerificationValidator#checkVerificationBlockedAndNotExpired(Verification)}
 * when verification is blocked.
 */
@Getter
@JsonAutoDetect(
        fieldVisibility = Visibility.NONE,
        setterVisibility = Visibility.NONE,
        getterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE,
        creatorVisibility = Visibility.NONE
)
public class ClientBlockedException extends RuntimeException {
    @JsonProperty
    private final long blockSeconds;

    /**
     * Class constructor initializing attributes for exception
     */
    public ClientBlockedException(long blockSeconds) {
        this.blockSeconds = blockSeconds;
    }
}
