package ru.astondevs.asber.userservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * Feign Client that provides endpoints of Deposit Service
 */
@FeignClient(name = "deposit-service")
public interface DepositServiceClient {

    /**
     * Method gets accounts count by client id
     * @param clientId client id from request
     * @return {@link Integer} with accounts count
     */
    @GetMapping("/api/v1/deposits-count")
    Integer getDepositsCount(@RequestParam UUID clientId);
}
