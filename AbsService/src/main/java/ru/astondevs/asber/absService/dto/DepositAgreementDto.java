package ru.astondevs.asber.absService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.absService.service.KafkaConsumer;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 *  Data transfer object that {@link  KafkaConsumer} gets from Kafka topic.
 *  Contains Deposit Agreement info to create {@link AbsDepositAgreementMessageDto}
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepositAgreementDto {
    /**
     * Product id.
     */
    @NotNull
    private Integer productId;
    /**
     * Agreement initialAmount.
     */
    @NotNull
    private BigDecimal initialAmount;
    /**
     * Card number.
     */
    @NotNull
    private String cardNumber;
    /**
     * Agreement autoRenewal.
     */
    @NotNull
    private Boolean autoRenewal;
    /**
     * Agreement interestRate.
     */
    @NotNull
    private BigDecimal interestRate;
    /**
     * Duration months for calculating start and end date.
     */
    @NotNull
    private Integer durationMonths;
}
