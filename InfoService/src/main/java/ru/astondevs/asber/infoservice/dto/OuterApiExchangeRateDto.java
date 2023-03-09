package ru.astondevs.asber.infoservice.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Dto for getting exchange rates from outer api.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OuterApiExchangeRateDto {

    /**
     * List of exchange rates.
     */
    private Map<String, Double> rates;
    /**
     * Base currency.
     */
    private String base;

}
