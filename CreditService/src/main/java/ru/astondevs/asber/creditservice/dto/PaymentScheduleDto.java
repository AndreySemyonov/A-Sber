package ru.astondevs.asber.creditservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object that is used in {@link CreditPaymentScheduleDto}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentScheduleDto {

    /**
     * Day of payment.
     */
    @NotNull
    private String paymentDate;
    /**
     * Principal payment on date.
     */
    @NotNull
    private String paymentPrincipal;
    /**
     * Interest payment on date.
     */
    @NotNull
    private String paymentInterest;

}
