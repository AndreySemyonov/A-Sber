package ru.astondevs.asber.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.userservice.controller.UserSettingsController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * Data transfer object that is used in response of
 * {@link UserSettingsController#changeSecurityQuestion(UUID, SecurityQuestionDto)} method of {@link UserSettingsController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SecurityQuestionDto {
    /**
     * Security question.
     */
    @NotNull
    @Size(min = 3, max = 50)
    @Pattern(regexp = "[а-яёА-ЯЁa-zA-Z\\d,!_\\.\\s\\?]+")
    private String securityQuestion;
    /**
     * Answer for security question.
     */
    @NotNull
    @Size(min = 3, max = 50)
    @Pattern(regexp = "[а-яёА-ЯЁa-zA-Z\\d,!_\\.\\s\\?]+")
    private String securityAnswer;
}
