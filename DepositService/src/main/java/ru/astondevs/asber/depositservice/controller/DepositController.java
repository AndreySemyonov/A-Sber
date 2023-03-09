package ru.astondevs.asber.depositservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.depositservice.dto.AccountResponseDto;
import ru.astondevs.asber.depositservice.dto.ClientCardsResponseDto;
import ru.astondevs.asber.depositservice.dto.ClientDepositResponseDto;
import ru.astondevs.asber.depositservice.dto.ClientDepositsResponseDto;
import ru.astondevs.asber.depositservice.entity.Account;
import ru.astondevs.asber.depositservice.entity.Agreement;
import ru.astondevs.asber.depositservice.entity.Card;
import ru.astondevs.asber.depositservice.entity.Product;
import ru.astondevs.asber.depositservice.mapper.AccountMapper;
import ru.astondevs.asber.depositservice.service.AccountService;
import ru.astondevs.asber.depositservice.service.AgreementService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Controller that handles requests to {@link AccountService} and return response using {@link AccountMapper}.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class DepositController implements DepositControllerApi {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final AgreementService agreementService;

    /**
     * End-point that gets set of accounts by client id, using {@link AccountService#getClientDeposits(UUID)}.
     * After this converting set of accounts to set of client deposits response dto, using
     * {@link AccountMapper#accountSetToClientDepositsResponseDtoSet(Set)}.
     * @param clientId Client id from request query parameters
     * @return {@link Set} of {@link ClientDepositsResponseDto} with information about all client deposit
     */
    @Override
    public ResponseEntity<Set<ClientDepositsResponseDto>> getDeposits(UUID clientId) {
        log.info("Request for set of ClientDepositsResponseDto with client id: {}", clientId);
        Set<ClientDepositsResponseDto> clientDepositsResponseDtos
                = accountMapper.accountSetToClientDepositsResponseDtoSet(accountService.getClientDeposits(clientId));
        log.info("Responding with set of ClientDepositsResponseDto with client id: {}", clientId);
        return ResponseEntity.ok(clientDepositsResponseDtos);
    }

    /**
     * End-point that gets account, agreement, product, card, using
     * {@link AccountService#getClientDeposit(UUID, UUID, UUID)}.
     * After this converting set of entities to client deposit response dto uses
     * {@link AccountMapper#toClientDepositResponseDto(Card, Agreement, Product)}
     * @param agreementId Agreement id from template variables in the request uri
     * @param clientId Client id from request query parameters
     * @param cardId Card id from request query parameters
     * @return {@link ClientDepositResponseDto} with information about client deposit
     */
    @Override
    public ResponseEntity<ClientDepositResponseDto> getDeposit(UUID agreementId, UUID clientId, UUID cardId) {
        log.info("Request for account with agreement id: {}, client id: {}, card id: {}", agreementId, clientId, cardId);
        Account clientDeposit = accountService.getClientDeposit(agreementId, clientId, cardId);
        log.info("Request for agreement with id: {}", agreementId);
        Agreement agreement = agreementService.getAgreementById(agreementId);
        log.info("Request for product");
        Product product = agreement.getProductId();
        log.info("Request for card");
        Card card = clientDeposit.getCards().stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Card not found by id = " + cardId));
        log.info("Responding with client deposit");
        return ResponseEntity.ok(accountMapper.toClientDepositResponseDto(card, agreement, product));
    }

    /**
     * End-point that gets set of accounts, using {@link AccountService#getClientCards(UUID)}.
     * After this converting set of accounts to set of client cards response dto, using
     * {@link AccountMapper#accountSetToClientCardsResponseDtoSet(Set)}
     * @param clientId Client id from request query parameters
     * @return {@link Set} of {@link ClientCardsResponseDto} with information about all client cards
     */
    @Override
    public ResponseEntity<Set<ClientCardsResponseDto>> getClientCards(UUID clientId) {
        log.info("Request for set of ClientCardsResponseDto with client id: {}", clientId);
        Set<ClientCardsResponseDto> clientCardsResponseDtos = accountMapper.accountSetToClientCardsResponseDtoSet(
                accountService.getClientCards(clientId));
        log.info("Responding with set of ClientCardsResponseDto with client id: {}", clientId);
        return ResponseEntity.ok(clientCardsResponseDtos);
    }

    /**
     * End-point that gets list of accounts, using {@link AccountService#getAccounts(UUID)}.
     * After this converting list of accounts to list of account response dto,
     * using {@link AccountMapper#accountListToAccountResponseDtoList(List)}
     * @param clientId Client id from request query parameters
     * @return {@link List} of {@link AccountResponseDto} with all client accounts
     */
    @Override
    public ResponseEntity<List<AccountResponseDto>> getAccounts(UUID clientId) {
        log.info("Request for list of AccountResponseDto with client id: {}", clientId);
        List<AccountResponseDto> accountResponseDtos = accountMapper.accountListToAccountResponseDtoList(
                accountService.getAccounts(clientId));
        log.info("Responding with list of AccountResponseDto with client id: {}", clientId);
        return ResponseEntity.ok(accountResponseDtos);
    }

    /**
     * End-point that gets list of accounts, using {@link AccountService#getDepositsCount(UUID)}.
     * using {@link AccountService#getDepositsCount(UUID)}
     * @param clientId Client id from request query parameters
     * @return {@link Integer} count of active accounts
     */
    @Override
    public ResponseEntity<Integer> getDepositsCount(UUID clientId) {
        log.info("Request for number of deposits by client id: {}", clientId);
        Integer depositsCount = accountService.getDepositsCount(clientId);
        log.info("Responding with number of deposits by client id: {}", clientId);
        return ResponseEntity.ok(depositsCount);
    }
}
