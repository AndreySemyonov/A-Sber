package ru.astondevs.asber.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.userservice.controller.UserController;
import ru.astondevs.asber.userservice.entity.enums.ClientStatus;

import java.util.UUID;

/**
 * Data transfer object that is used in response of
 * {@link UserController#getUserRegistrationStatus(String)} method of {@link UserController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusRegistrationDto {
    /**
     * Mobile phone.
     */
    private String mobilePhone;
    /**
     * Client status.
     */
    private ClientStatus clientStatus;
    /**
     * Client id.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID clientId;
}
