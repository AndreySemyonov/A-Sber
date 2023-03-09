package ru.astondevs.asber.creditservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.creditservice.controller.CreditOrderController;
import ru.astondevs.asber.creditservice.entity.enums.CreditStatus;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data transfer object that is used in response of
 * {@link CreditOrderController#getCreditOrdersByClientId(UUID)} method of
 * {@link CreditOrderController}.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreditOrderResponseDto {

    /**
     * Credit order id.
     */
    @NotNull
    private UUID id;
    /**
     * Product id.
     */
    @NotNull
    private Integer productId;
    /**
     * Credit order status.
     */
    @NotNull
    private CreditStatus status;
    /**
     * Credit order amount.
     */
    @NotNull
    private BigDecimal amount;
    /**
     * Credit order period months.
     */
    @NotNull
    private Integer periodMonths;
    /**
     * Credit order creation date.
     */
    @NotNull
    private LocalDate creationDate;
}
