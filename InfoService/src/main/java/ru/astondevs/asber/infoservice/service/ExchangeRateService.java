package ru.astondevs.asber.infoservice.service;

import java.util.List;
import ru.astondevs.asber.infoservice.dto.ExchangeRateDto;
import ru.astondevs.asber.infoservice.entity.ExchangeRate;
import ru.astondevs.asber.infoservice.repository.ExchangeRateRepository;

/**
 * Interface that works with {@link ExchangeRate} entity.
 */
public interface ExchangeRateService {

    /**
     * Method that gets {@link ExchangeRate} from {@link ExchangeRateRepository}
     *
     * @param currencyCode - currency code(3 letters, ex. USD)
     * @return {@link ExchangeRate}
     */
    ExchangeRate getExchangeRateByCurrencyCode(String currencyCode);

    /**
     * Method that updates {@link ExchangeRate} if it exists and creates it if it doesn't exist
     *
     * @param exchangeRateDto - {@link ExchangeRateDto} of {@link ExchangeRate}
     * @return Updated or created {@link ExchangeRate}
     */
    ExchangeRate updateOrCreateExchangeRate(ExchangeRateDto exchangeRateDto);

    /**
     * Method that gets all available exchange rates from database.
     *
     * @return List of {@link ExchangeRate}
     */
    List<ExchangeRate> getAllExchangeRates();
}
