package ru.astondevs.asber.absService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.absService.enums.DigitalWallet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *  Data transfer object that contains full info to create a card
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewCardResponseDto {
    /**
     * Account number.
     */
    @NotNull
    @Size(max = 20)
    private String accountNumber;
    /**
     * Card transaction limit.
     */
    @NotNull
    private BigDecimal transactionLimit;
    /**
     * Card expiration date
     */
    @NotNull
    private LocalDate expirationDate;
    /**
     * Card holderName
     */
    @NotNull
    @Size(max = 50)
    private String holderName;
    /**
     * Card digitalWallet
     */
    private DigitalWallet digitalWallet;
    /**
     * Card product id
     */
    @NotNull
    private Integer cardProductId;

    @NotNull
    private String cardNumber;

    @NotNull
    private Boolean isDefault;
}
