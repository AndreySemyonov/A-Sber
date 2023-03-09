package ru.astondevs.asber.moneytransferservice.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.moneytransferservice.controller.InfoController;
import ru.astondevs.asber.moneytransferservice.dto.AccountNumberDto;
import ru.astondevs.asber.moneytransferservice.service.CardService;
import ru.astondevs.asber.moneytransferservice.service.DepositServiceClient;
import ru.astondevs.asber.moneytransferservice.util.exception.ExternalServiceRequestException;
import ru.astondevs.asber.moneytransferservice.util.exception.NotFoundException;

/**
 * Service implementation of {@link CardService} for {@link InfoController}
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {
    private final DepositServiceClient depositServiceClient;

    @Override
    public AccountNumberDto getAccountNumberIfCardNumberIsPresent(String cardNumber) {
        try {
            return depositServiceClient.isCardNumberValid(cardNumber);
        } catch (FeignException.NotFound e) {
            throw new NotFoundException();
        } catch (Exception e1) {
            throw new ExternalServiceRequestException();
        }
    }
}
