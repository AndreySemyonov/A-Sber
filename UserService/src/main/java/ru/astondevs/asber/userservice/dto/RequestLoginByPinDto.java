package ru.astondevs.asber.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.userservice.controller.AuthenticationController;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Data transfer object that is used in response of
 * {@link AuthenticationController#login(RequestLoginByPinDto)} method of {@link AuthenticationController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestLoginByPinDto {
    /**
     * Fingerprint.
     */
    @NotNull
    private String fingerprint;
    /**
     * Client id.
     */
    @NotNull
    private UUID clientId;
}
