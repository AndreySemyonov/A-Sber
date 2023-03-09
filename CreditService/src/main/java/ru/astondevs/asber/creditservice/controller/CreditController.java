package ru.astondevs.asber.creditservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.creditservice.dto.ClientCreditResponseDto;
import ru.astondevs.asber.creditservice.dto.CreditPaymentScheduleDto;
import ru.astondevs.asber.creditservice.dto.CreditResponseDto;
import ru.astondevs.asber.creditservice.dto.CreditsForClientCredit;
import ru.astondevs.asber.creditservice.dto.PaymentScheduleDto;
import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.Agreement;
import ru.astondevs.asber.creditservice.entity.Card;
import ru.astondevs.asber.creditservice.entity.PaymentSchedule;
import ru.astondevs.asber.creditservice.mapper.AccountMapper;
import ru.astondevs.asber.creditservice.mapper.CardMapper;
import ru.astondevs.asber.creditservice.mapper.PaymentScheduleMapper;
import ru.astondevs.asber.creditservice.service.AccountService;
import ru.astondevs.asber.creditservice.service.AgreementService;
import ru.astondevs.asber.creditservice.service.CreditService;
import ru.astondevs.asber.creditservice.service.PaymentScheduleService;

import java.util.List;
import java.util.UUID;

/**
 * Controller that handles requests to {@link CreditService}, {@link AccountService},
 * {@link AgreementService}, {@link PaymentScheduleService} and return response using
 * {@link CardMapper}, {@link PaymentScheduleMapper}, {@link AccountMapper}.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api("Operations with credits")
@RequestMapping("/api/v1/credits")
public class CreditController {

    private final CreditService creditService;
    private final AccountService accountService;
    private final AgreementService agreementService;
    private final PaymentScheduleService paymentScheduleService;
    private final CardMapper cardMapper;
    private final PaymentScheduleMapper paymentScheduleMapper;
    private final AccountMapper accountMapper;

    /**
     * End-point that gets all accounts by client id, using
     * {@link CreditService#getClientCredits(UUID)}. After this converting list of accounts to list
     * of client credit response dto, using
     * {@link AccountMapper#creditsListToCreditsForClientCreditList(List)} and create client credit
     * response dto.
     *
     * @param clientId Client id from request query parameters
     * @return {@link ClientCreditResponseDto} with all information about client credits
     */
    @GetMapping
    @ApiOperation(value = "Get client's credits by client's id",
        authorizations = {@Authorization(value = "Authorization")})
    public ClientCreditResponseDto getClientCredits(@RequestParam UUID clientId) {
        log.info("Request for credits with client id: {}", clientId);
        List<Account> clientCredits = creditService.getClientCredits(clientId);
        List<CreditsForClientCredit> credits = accountMapper
            .creditsListToCreditsForClientCreditList(clientCredits);
        log.info("Responding with list of credits with client id: {}, list size: {}", clientId,
            credits.size());
        return ClientCreditResponseDto.builder().credits(credits).build();
    }

    /**
     * End-point that gets credit card by credit id, using {@link CreditService#getCredit(UUID)}.
     * After this converting credit card to credit response dto, using
     * {@link CardMapper#cardToCreditResponseDto(Card)}.
     *
     * @param creditId Credit id from request uri
     * @return {@link CreditResponseDto} with client credit
     */
    @GetMapping("/{creditId}")
    @ApiOperation(value = "Get specific credit by id",
        authorizations = {@Authorization(value = "Authorization")})
    public CreditResponseDto getCredit(@PathVariable UUID creditId) {
        log.info("Request for credit card with credit id: {}", creditId);
        Card creditCard = creditService.getCredit(creditId);
        log.info("Responding with credit card with credit id: {}", creditId);
        return cardMapper.cardToCreditResponseDto(creditCard);
    }

    /**
     * End-point that gets account, agreement, list of payment schedule, using
     * {@link AccountService#getAccountByCreditId(UUID)},
     * {@link AgreementService#getAgreementByCreditId(UUID)},
     * {@link PaymentScheduleService#getPaymentSchedulesByAccountId(UUID)} After this converting set
     * of entities to credit payment schedule dto, using
     * {@link PaymentScheduleMapper#toCreditPaymentScheduleDto(List, Account, Agreement)}.
     *
     * @param creditId Credit id from request uri
     * @return {@link CreditPaymentScheduleDto} with information about credit payment schedule
     */
    @GetMapping("/{creditId}/schedule")
    @ApiOperation(value = "Get credit schedule by credit's id",
        authorizations = {@Authorization(value = "Authorization")})
    public CreditPaymentScheduleDto getCreditSchedule(@PathVariable UUID creditId) {
        log.info("Request for credit schedule with credit id: {}", creditId);

        Account account = accountService.getAccountByCreditId(creditId);
        Agreement agreement = agreementService.getAgreementByCreditId(creditId);

        List<PaymentSchedule> paymentSchedules = paymentScheduleService.getPaymentSchedulesByAccountId(
            account.getId());
        List<PaymentScheduleDto> payments = paymentScheduleMapper.toPaymentScheduleDtoList(
            paymentSchedules);

        CreditPaymentScheduleDto creditPaymentScheduleDto = paymentScheduleMapper.toCreditPaymentScheduleDto(
            payments,
            account,
            agreement
        );
        log.info("Returning CreditPaymentDto for credit id: {}", creditId);
        return creditPaymentScheduleDto;
    }

    /**
     * End-point that gets list of accounts, using {@link AccountService#getCreditsCount(UUID)}.
     * using {@link AccountService#getCreditsCount(UUID)}
     *
     * @param clientId Client id from request query parameters
     * @return {@link Integer} count of active accounts
     */
    @GetMapping("/credits-count")
    public Integer getCreditsCount(@RequestParam UUID clientId) {
        log.info("Request for credits count with client id: {}", clientId);
        Integer creditCount = accountService.getCreditsCount(clientId);
        log.info("Returning credit count: {} for client with id: {}", creditCount, clientId);
        return creditCount;
    }
}
