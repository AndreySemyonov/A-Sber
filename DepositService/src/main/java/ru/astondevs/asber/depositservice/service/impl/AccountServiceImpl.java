package ru.astondevs.asber.depositservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.depositservice.entity.Account;
import ru.astondevs.asber.depositservice.entity.Agreement;
import ru.astondevs.asber.depositservice.entity.Card;
import ru.astondevs.asber.depositservice.repository.AccountRepository;
import ru.astondevs.asber.depositservice.service.AccountService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Implementation of {@link AccountService}.
 * Works with {@link AccountRepository} and {@link Account}.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    /**
     * {@inheritDoc}
     * For getting data from db uses method {@link AccountRepository#findActiveAccountsWithAgreementsAndCards(UUID)}.
     * @param clientId Client id from {@link Account}
     * @return {@link Set} of {@link Account}
     */
    @Override
    public Set<Account> getClientDeposits(UUID clientId) {
        return accountRepository.findActiveAccountsWithAgreementsAndCards(clientId);
    }

    /**
     * {@inheritDoc}
     * For getting data from db uses method {@link AccountRepository#findActiveAccountsWithCards(UUID)}.
     * @param clientId Client id from {@link Account}
     * @return {@link Set} of {@link Account}
     */
    @Override
    public Set<Account> getClientCards(UUID clientId) {
        return accountRepository.findActiveAccountsWithCards(clientId);
    }

    /**
     * {@inheritDoc}
     * For getting data from db uses method {@link AccountRepository#findAccountsByClientId(UUID)}.
     * @param clientId Client id from {@link Account}
     * @return {@link List} of {@link Account}
     */
    @Override
    public List<Account> getAccounts(UUID clientId) {
        return accountRepository.findAccountsByClientId(clientId);
    }

    /**
     * {@inheritDoc}
     * For getting data from db uses method {@link AccountRepository#findAccountByAccountNumber(String)}.
     * @param accountNumber Account number from {@link Account}
     * @return {@link Account}
     * @throws EntityNotFoundException if account not found
     */
    @Override
    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account not found by accountNumber = " + accountNumber));
    }

    /**
     * {@inheritDoc}
     * For getting data from db uses method {@link AccountRepository#findAccountByCardNumber(String)}.
     * @param cardNumber Card number from {@link Card}
     * @return {@link Account}
     * @throws EntityNotFoundException if account not found
     */
    @Override
    public Account getAccountByCardNumber(String cardNumber) {
        return accountRepository.findAccountByCardNumber(cardNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account not found by cardNumber = " + cardNumber));
    }

    /**
     * {@inheritDoc}
     * For getting data from db uses method {@link AccountRepository#findActiveAccount(UUID, UUID, UUID)}.
     * @param agreementId Agreement id from {@link Agreement}
     * @param clientId Client id from {@link Account}
     * @param cardId Card id from {@link Card}
     * @return {@link Account}
     * @throws EntityNotFoundException if account not found
     */
    @Override
    public Account getClientDeposit(UUID agreementId, UUID clientId, UUID cardId) {
        return accountRepository.findActiveAccount(clientId, agreementId, cardId)
                .orElseThrow(() -> new EntityNotFoundException("Deposit not found for client with id = " + clientId));
    }

    /**
     * {@inheritDoc}
     * For getting data from db uses method {@link AccountRepository#findActiveAccountsCountByClientId(UUID)}.
     * @param clientId Client id from {@link Account}
     * @return {@link Integer}
     */
    @Override
    public Integer getDepositsCount(UUID clientId) {
        return accountRepository.findActiveAccountsCountByClientId(clientId);
    }
}
