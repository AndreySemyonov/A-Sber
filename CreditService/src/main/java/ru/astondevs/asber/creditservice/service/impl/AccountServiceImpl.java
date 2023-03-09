package ru.astondevs.asber.creditservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.Credit;
import ru.astondevs.asber.creditservice.repository.AccountRepository;
import ru.astondevs.asber.creditservice.service.AccountService;

import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;

/**
 * Implementation of {@link AccountService}. Works with {@link AccountRepository} and
 * {@link Account}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    /**
     * {@inheritDoc} For getting data from db uses method
     * {@link AccountRepository#findAccountByCreditId(UUID)}.
     *
     * @param creditId Credit id from {@link Credit}
     * @return {@link Account}
     * @throws EntityNotFoundException if account not found
     */
    @Override
    public Account getAccountByCreditId(UUID creditId) {
        log.info("Request for account with credit id: {}", creditId);
        Optional<Account> optionalAccount = accountRepository.findAccountByCreditId(creditId);
        if (optionalAccount.isPresent()) {
            log.info("Returning account with account id: {}", optionalAccount.get().getId());
            return optionalAccount.get();
        } else {
            log.error("No account with creditId: {}", creditId);
            throw new EntityNotFoundException();
        }
    }

    /**
     * {@inheritDoc} For getting data from db uses method
     * {@link AccountRepository#findActiveAccountsCountByClientId(UUID)}.
     *
     * @param clientId Client id from {@link Account}
     * @return {@link Integer}
     */
    @Override
    public Integer getCreditsCount(UUID clientId) {
        log.info("Request for credit count with client: {}", clientId);
        Integer creditCount = accountRepository.findActiveAccountsCountByClientId(
            clientId);
        log.info("Returning credit count: {}", creditCount);
        return creditCount;
    }
}
