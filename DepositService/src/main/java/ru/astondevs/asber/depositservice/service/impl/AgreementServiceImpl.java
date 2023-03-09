package ru.astondevs.asber.depositservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.depositservice.dto.AbsDepositAgreementMessageDto;
import ru.astondevs.asber.depositservice.dto.AccountNumberDto;
import ru.astondevs.asber.depositservice.dto.DepositAgreementDto;
import ru.astondevs.asber.depositservice.entity.Account;
import ru.astondevs.asber.depositservice.entity.Agreement;
import ru.astondevs.asber.depositservice.entity.Product;
import ru.astondevs.asber.depositservice.mapper.AgreementMapper;
import ru.astondevs.asber.depositservice.mapper.CardMapper;
import ru.astondevs.asber.depositservice.repository.AccountRepository;
import ru.astondevs.asber.depositservice.repository.AgreementRepository;
import ru.astondevs.asber.depositservice.repository.ProductRepository;
import ru.astondevs.asber.depositservice.service.AccountService;
import ru.astondevs.asber.depositservice.service.AgreementService;
import ru.astondevs.asber.depositservice.service.KafkaProducer;
import ru.astondevs.asber.depositservice.service.ProductService;
import ru.astondevs.asber.depositservice.service.generators.AgreementNumberGenerator;
import ru.astondevs.asber.depositservice.util.exception.CanNotChangeAgreementStatusException;
import ru.astondevs.asber.depositservice.util.exception.CanNotChangeRenewalStatusException;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link AgreementService}.
 * Works with services {@link AccountService}, {@link ProductService}, component {@link AgreementNumberGenerator},
 * repositories {@link AgreementRepository}, {@link AccountRepository},
 * entities {@link Agreement}, {@link Product}, {@link Account}
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgreementServiceImpl implements AgreementService {

    private final AgreementRepository agreementRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final KafkaProducer kafkaProducer;
    private final ProductRepository productRepository;
    private final AgreementMapper agreementMapper;

    private final CardMapper cardMapper;

    /**
     * Method that sends {@link DepositAgreementDto} to AbsService via Kafka.
     * @param depositAgreementDto {@link DepositAgreementDto}
     */
    public void sendDepositAgreementDtoToAbs(DepositAgreementDto depositAgreementDto) {
        kafkaProducer.sendDepositAgreementDto(depositAgreementDto);
    }

    /**
     * {@inheritDoc}
     * For updates data in db uses method {@link AgreementRepository#save(Object)}.
     * Before this getting agreement uses {@link AgreementRepository#findById(Object)}
     * @param agreementId Agreement id from {@link Agreement}
     * @param clientId Client id from {@link Account}
     * @param autoRenewal {@link Boolean} updating value
     * @throws EntityNotFoundException if agreement not found
     */
    @Override
    @Transactional
    public void changeAutoRenewalStatus(UUID agreementId, UUID clientId, Boolean autoRenewal) {
        Agreement agreement = agreementRepository.findById(agreementId).orElseThrow(EntityNotFoundException::new);
        if (Objects.equals(agreement.getAutoRenewal(), autoRenewal)) {
            return;
        }
        if (!agreement.getAutoRenewal() && agreement.getProductId().getActiveUntil() != null) {
            LocalDateTime agreementEndDate = agreement.getEndDate();
            LocalDate productEndDate = agreement.getProductId().getActiveUntil();
            if (productEndDate.isBefore(ChronoLocalDate.from(agreementEndDate))) {
                throw new CanNotChangeRenewalStatusException();
            }
        }
        agreement.setAutoRenewal(autoRenewal);
        agreementRepository.save(agreement);
    }

    /**
     * {@inheritDoc}
     * For updates data in db uses methods {@link AccountRepository#save(Object)} and
     * {@link AgreementRepository#save(Object)}.
     * Before this getting agreement uses {@link AgreementRepository#findById(Object)}
     * @param agreementId Agreement id from {@link Agreement}
     * @param accountNumberDto {@link AccountNumberDto}
     * @throws EntityNotFoundException if agreement not found
     * @throws CanNotChangeAgreementStatusException if getting agreement is not active
     */
    @Transactional
    @Override
    public void revokeDeposit(UUID agreementId, AccountNumberDto accountNumberDto) {
        Agreement agreement = agreementRepository.findById(agreementId).orElseThrow(EntityNotFoundException::new);
        if (!agreement.getIsActive()) {
            throw new CanNotChangeAgreementStatusException();
        } else {
            Account account = accountService.getAccountByAccountNumber(accountNumberDto.getAccountNumber());
            agreement.setIsActive(false);
            account.setCurrentBalance(account.getCurrentBalance().add(agreement.getCurrentBalance()));
            agreement.setCurrentBalance(BigDecimal.ZERO);
            agreement.setEndDate(LocalDateTime.now());
            accountRepository.save(account);
            agreementRepository.save(agreement);
        }
    }

    /**
     * For getting data from db uses method {@link AgreementRepository#findById(Object)}.
     * Updates Auto Renewal Status from {@link Agreement} if {@link Product} is not active.
     * @param agreementId Agreement id from {@link Agreement}
     * @return {@link Agreement}
     */
    @Transactional
    @Override
    public Agreement getAgreementById(UUID agreementId) {
        log.info("Request for agreement with id: {}", agreementId);
        Agreement agreement = agreementRepository.findById(agreementId)
                .orElseThrow(() -> new EntityNotFoundException("Agreement not found by id = " + agreementId));
        log.info("Request for product");
        Product product = agreement.getProductId();
        Boolean isRenewalActive = agreement.getAutoRenewal();
        if (!product.getIsActive() && isRenewalActive) {
            log.info("Request for changing agreement auto renewal status to false");
            agreement.setAutoRenewal(false);
        }
        log.info("Returning agreement with id: {}", agreementId);
        return agreement;
    }

    /**
     * Method that maps {@link AbsDepositAgreementMessageDto} to {@link Agreement}
     *
     * @param message - {@link AbsDepositAgreementMessageDto}
     * @return {@link Agreement}
     */
    @Override
    public Agreement createAgreementFromAbs(AbsDepositAgreementMessageDto message) {
        Account account = accountService.getAccountByCardNumber(message.getCardNumber());
        Product product = productRepository.findById(message.getProductId()).orElseThrow(EntityNotFoundException::new);

        Agreement agreement = agreementMapper.agreementFromAbsDepositAgreementMessageDto(message);

        agreement.setAccountId(account);
        agreement.setProductId(product);

        agreementRepository.save(agreement);

        return agreement;
    }

}
