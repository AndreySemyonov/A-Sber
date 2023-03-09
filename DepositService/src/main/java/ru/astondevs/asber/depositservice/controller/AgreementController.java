package ru.astondevs.asber.depositservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.depositservice.dto.AccountNumberDto;
import ru.astondevs.asber.depositservice.dto.DepositAgreementDto;
import ru.astondevs.asber.depositservice.mapper.AgreementMapper;
import ru.astondevs.asber.depositservice.service.AgreementService;

import java.util.UUID;

/**
 * Controller that handles requests to {@link AgreementService} and return response using {@link AgreementMapper}.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AgreementController implements AgreementControllerApi {

    private final AgreementService agreementService;

    /**
     * Sends {@link DepositAgreementDto} to Kafka
     * @param depositAgreementDto {@link DepositAgreementDto}
     */
    @Override
    public ResponseEntity<Void> createNewDepositAgreement(DepositAgreementDto depositAgreementDto) {
        agreementService.sendDepositAgreementDtoToAbs(depositAgreementDto);
        return ResponseEntity.ok().build();
    }

    /**
     * End-point that updates auto-renewal status for finding client agreement by agreement id,
     * using {@link AgreementService#changeAutoRenewalStatus(UUID, UUID, Boolean)}.
     * @param agreementId Agreement id from template variables in the request uri
     * @param clientId Client id from request query parameters
     * @param autoRenewal {@link Boolean} value from request query parameters
     */
    @Override
    public ResponseEntity<Void> changeAutoRenewalStatus(UUID agreementId, UUID clientId, Boolean autoRenewal) {
        log.info("Request for changing auto renewal status of agreement with id: {} and client id: {}",
                agreementId, clientId);
        agreementService.changeAutoRenewalStatus(agreementId, clientId, autoRenewal);
        return ResponseEntity.ok().build();
    }

    /**
     * End-point that revokes deposit for finding client agreement by agreement id
     * @param agreementId Agreement id from template variables in the request uri
     * @param accountNumberDto {@link AccountNumberDto} from request body with account number
     */
    @Override
    public ResponseEntity<Void> revokeDeposit(UUID agreementId, AccountNumberDto accountNumberDto) {
        log.info("Request for revoking the deposit with agreement id: {}", agreementId);
        agreementService.revokeDeposit(agreementId, accountNumberDto);
        return ResponseEntity.ok().build();
    }
}
