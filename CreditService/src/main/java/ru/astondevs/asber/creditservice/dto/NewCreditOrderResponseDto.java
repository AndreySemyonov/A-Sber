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
 * Response DTO of {@link CreditOrder}
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewCreditOrderResponseDto {

    /**
     * Credit order id.
     */
    @NotNull
    private String id;
    /**
     * Credit order product id.
     */
    @NotNull
    private String productId;

    /**
     * Credit order status.
     */
    @NotNull
    private String status;
    /**
     * Credit order amount.
     */
    @NotNull
    private Number amount;
    /**
     * Credit order period in months.
     */
    @NotNull
    private Number periodMonths;
    /**
     * Credit order creation date.
     */
    @NotNull
    private String creationDate;
}
