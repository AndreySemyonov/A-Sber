package ru.astondevs.asber.infoservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.astondevs.asber.infoservice.dto.ExchangeRateDto;
import ru.astondevs.asber.infoservice.entity.ExchangeRate;
import ru.astondevs.asber.infoservice.mapper.ExchangeRateMapper;
import ru.astondevs.asber.infoservice.repository.ExchangeRateRepository;
import ru.astondevs.asber.infoservice.service.ExchangeRateService;

/**
 * Service that works with {@link ExchangeRate} entity.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    private final ExchangeRateMapper exchangeRateMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public ExchangeRate getExchangeRateByCurrencyCode(String currencyCode) {
        log.info("Request for currency exchange rate with currency code: {}", currencyCode);
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findByCurrencyCode(
            currencyCode);

        if (optionalExchangeRate.isEmpty()) {
            log.error("Exchange rate for currency with currency code: {} is not found",
                currencyCode);
            throw new EntityNotFoundException();
        }

        ExchangeRate exchangeRate = optionalExchangeRate.get();
        log.info("Returning currency exchange rate with id: {}", exchangeRate.getId());
        return exchangeRate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExchangeRate updateOrCreateExchangeRate(ExchangeRateDto exchangeRateDto) {
        ExchangeRate exchangeRate = exchangeRateMapper.toExchangeRate(exchangeRateDto);

        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findByCurrencyCode(
            exchangeRateDto.getCurrencyCode());

        if (optionalExchangeRate.isEmpty()) {
            log.info("Saving exchange rate to Db with currency code: {}",
                exchangeRate.getCurrencyCode());
            exchangeRate.setIsCross(true); // todo пока неизвестно откуда это брать
            exchangeRate.setSign("sign"); // todo пока неизвестно откуда это брать
            exchangeRate.setIsoCode("iso"); // todo пока неизвестно откуда это брать
            return exchangeRateRepository.save(exchangeRate);
        }

        UUID exchangeRateId = optionalExchangeRate.get().getId();
        log.info("Updating exchange rate with id: {}", exchangeRateId);
        exchangeRate.setId(exchangeRateId);
        return exchangeRateRepository.save(exchangeRate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ExchangeRate> getAllExchangeRates() {
        log.info("Getting all exchange rates");
        return exchangeRateRepository.findAll();
    }
}
