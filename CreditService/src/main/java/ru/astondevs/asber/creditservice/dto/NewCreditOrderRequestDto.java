package ru.astondevs.asber.creditservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.creditservice.entity.CreditOrder;

import javax.validation.constraints.NotNull;

/**
 * Request DTO of {@link CreditOrder}
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewCreditOrderRequestDto {

    /**
     * Client id.
     */
    @NotNull
    private String clientId;

    /**
     * Credit order number.
     */
    @NotNull
    private String number;

    /**
     * Credit order product id.
     */
    @NotNull
    private Number productId;

    /**
     * Credit order amount.
     */
    @NotNull
    private Number amount;

    /**
     * Credit order period
     */
    @NotNull
    private Number periodMonths;

    /**
     * Credit order creation date.
     */
    @NotNull
    private String creationDate;

    /**
     * Client monthly income
     */
    @NotNull
    private Number monthlyIncome;

    /**
     * Client monthly expenditure
     */
    @NotNull
    private Number monthlyExpenditure;

    /**
     * employee identification number.
     */
    @NotNull
    private String employerIdentificationNumber;
}
