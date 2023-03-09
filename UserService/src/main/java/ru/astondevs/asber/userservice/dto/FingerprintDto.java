package ru.astondevs.asber.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.userservice.controller.AuthenticationController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

/**
 * Data transfer object that is used in response of
 * {@link AuthenticationController#saveFingerprint(UUID, FingerprintDto)}
 * and {@link AuthenticationController#login(UUID, FingerprintDto, String)} of {@link AuthenticationController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FingerprintDto {
    /**
     * Fingerprint.
     */
    @NotNull
    @Pattern(regexp = "\\d{6}", message = "Invalid format. Pin should contain 6 digits")
    private String fingerprint;

}
