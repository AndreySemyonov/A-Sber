package ru.astondevs.asber.depositservice.service;

import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.asber.depositservice.dto.AbsDepositAgreementMessageDto;
import ru.astondevs.asber.depositservice.dto.AccountNumberDto;
import ru.astondevs.asber.depositservice.dto.DepositAgreementDto;
import ru.astondevs.asber.depositservice.entity.Account;
import ru.astondevs.asber.depositservice.entity.Agreement;

import java.util.UUID;

/**
 * Service for {@link Agreement}.
 */
public interface AgreementService {
    /**
     * Method that sends {@link DepositAgreementDto} to AbsService via Kafka.
     * @param depositAgreementDto {@link DepositAgreementDto}
     */
    void sendDepositAgreementDtoToAbs(DepositAgreementDto depositAgreementDto);

    /**
     * Method that updates value of auto renewal status.
     * @param agreementId Agreement id from {@link Agreement}
     * @param clientId Client id from {@link Account}
     * @param autoRenewal {@link Boolean} updating value
     */
    void changeAutoRenewalStatus(UUID agreementId, UUID clientId, Boolean autoRenewal);

    /**
     * Method that revokes deposit agreement by account number from DepositRevocationDto.
     * @param agreementId Agreement id from {@link Agreement}
     * @param accountNumberDto {@link AccountNumberDto}
     */
    @Transactional
    void revokeDeposit(UUID agreementId, AccountNumberDto accountNumberDto);

    /**
     * Method that find agreement by agreement id.
     * @param agreementId Agreement id from {@link Agreement}
     * @return {@link Agreement}
     */
    Agreement getAgreementById(UUID agreementId);

    /**
     * Method that maps {@link AbsDepositAgreementMessageDto} to {@link Agreement}
     *
     * @param message - {@link AbsDepositAgreementMessageDto}
     * @return {@link Agreement}
     */
    Agreement createAgreementFromAbs(AbsDepositAgreementMessageDto message);
}
