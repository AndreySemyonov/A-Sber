package ru.astondevs.asber.creditservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.creditservice.controller.CreditController;

import java.util.List;
import java.util.UUID;

/**
 * Data transfer object that is used in response of {@link CreditController#getClientCredits(UUID)}
 * method of {@link CreditController}.
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreditResponseDto {

    /**
     * List of credits.
     *
     * @see CreditsForClientCredit
     */
    List<CreditsForClientCredit> credits;

}
