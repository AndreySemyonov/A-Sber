package ru.astondevs.asber.creditservice.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.Card;
import ru.astondevs.asber.creditservice.entity.Credit;
import ru.astondevs.asber.creditservice.entity.CreditOrder;
import ru.astondevs.asber.creditservice.repository.AccountRepository;
import ru.astondevs.asber.creditservice.repository.CardRepository;
import ru.astondevs.asber.creditservice.service.CreditService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of {@link CreditService}. Works with {@link CreditService}, {@link CardRepository}
 * and {@link Account}, {@link Card}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreditServiceImpl implements CreditService {

    private final AccountRepository accountRepository;

    private final CardRepository cardRepository;

    /**
     * {@inheritDoc} For getting data from db uses method
     * {@link AccountRepository#findAccountAndActiveCreditAndAgreementAndProductByClientId(UUID)}.
     *
     * @param clientId Client id from {@link CreditOrder}
     * @return {@link List} of {@link Account}
     */
    @Override
    public List<Account> getClientCredits(UUID clientId) {
        log.info("Request for credits with client id: {}", clientId);
        List<Account> accountList = accountRepository.findAccountAndActiveCreditAndAgreementAndProductByClientId(
            clientId);
        log.info("Returning list of credits with client id: {}, list size: {}", clientId,
            accountList.size());
        return accountList;
    }

    /**
     * {@inheritDoc} For getting data from db uses method
     * {@link CardRepository#getFullCardInfoByCreditId(UUID)}
     *
     * @param creditId Credit id from {@link Credit}
     * @return {@link Card}
     * @throws EntityNotFoundException if card not found
     */
    @Override
    public Card getCredit(UUID creditId) {
        log.info("Request for credit card info with credit id: {}", creditId);
        Optional<Card> optionalCard = cardRepository.getFullCardInfoByCreditId(creditId);
        if (optionalCard.isEmpty()) {
            log.error("No card with credit id: {}", creditId);
            throw new EntityNotFoundException();
        }
        Card card = optionalCard.get();
        log.info("Returning card with id: {}", card.getId());
        return card;
    }
}
