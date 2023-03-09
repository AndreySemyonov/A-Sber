package ru.astondevs.asber.moneytransferservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.astondevs.asber.moneytransferservice.dto.ExchangeRateDto;

import java.util.Map;

/**
 * Feign Client that provides endpoints of Info Service
 */
@FeignClient(name = "info-service")
public interface InfoServiceClient {

    /**
     * Method gets account number if card is found
     * @param currencyCodeJson map with currency code (3 letters, ex. USD)
     * @return {@link ExchangeRateDto} with account number
     */
    @PostMapping("/exchange-rates")
    ExchangeRateDto getExchangeRate(@RequestBody Map<String, String> currencyCodeJson);
}
