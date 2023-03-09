package ru.astondevs.asber.absService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Data transfer object that contains information about Deposit Agreement
 * */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AbsDepositAgreementMessageDto {
    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private String agreementNumber;

    @NotNull
    private Integer productId;

    @NotNull
    private Boolean isActive;

    @NotNull
    private BigDecimal initialAmount;

    @NotNull
    private String cardNumber;

    @NotNull
    private Boolean autoRenewal;

    @NotNull
    private BigDecimal interestRate;
}
