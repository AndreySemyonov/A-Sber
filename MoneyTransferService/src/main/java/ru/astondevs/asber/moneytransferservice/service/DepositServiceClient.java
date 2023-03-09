package ru.astondevs.asber.moneytransferservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.astondevs.asber.moneytransferservice.dto.AccountNumberDto;

/**
 * Feign Client that provides endpoints of Deposit Service
 */
@FeignClient(name = "deposit-service")
public interface DepositServiceClient {

    /**
     * Method gets account number if card is found
     * @param cardNumber card number from request
     * @return {@link AccountNumberDto} with account number
     */
    @GetMapping("/api/v1/account-number")
    AccountNumberDto isCardNumberValid(@RequestParam String cardNumber);
}
