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
import javax.validation.constraints.NotNull;

/**
 * Data transfer object that is used in response of
 * {@link CreditController#getCreditSchedule(UUID) getCreditSchedule} method of
 * {@link CreditController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreditPaymentScheduleDto {

    /**
     * Account number.
     */
    @NotNull
    private String accountNumber;
    /**
     * Agreement id.
     */
    @NotNull
    private String agreementId;
    /**
     * Principal debt.
     */
    @NotNull
    private String principalDebt;

    /**
     * Interest debt.
     */
    @NotNull
    private String interestDebt;
    /**
     * List of scheduled payments.
     */
    @NotNull
    private List<PaymentScheduleDto> payments;
}
