package ru.astondevs.asber.depositservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.depositservice.dto.AccountNumberDto;
import ru.astondevs.asber.depositservice.dto.CardStatusDto;
import ru.astondevs.asber.depositservice.dto.NewCardRequestDto;
import ru.astondevs.asber.depositservice.entity.Account;
import ru.astondevs.asber.depositservice.mapper.AccountMapper;
import ru.astondevs.asber.depositservice.mapper.CardMapper;
import ru.astondevs.asber.depositservice.service.AccountService;
import ru.astondevs.asber.depositservice.service.CardService;
import ru.astondevs.asber.depositservice.service.KafkaProducer;

import java.util.UUID;

/**
 * Controller that handles requests to {@link CardService} and return response using {@link CardMapper}.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CardController implements CardControllerApi {

    private final CardService cardService;
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final KafkaProducer kafkaProducer;

    /**
     * End-point that changes card status by card id, getting value from received request body.
     * For sending this into db uses {@link CardService#changeCardStatus(UUID,CardStatusDto)}.
     * @param cardStatusDto {@link CardStatusDto} from request body with card number and card status
     * @param cardId Card id from request query parameters
     */
    @Override
    public ResponseEntity<Void> changeCardStatus(UUID cardId, CardStatusDto cardStatusDto) {
        log.info("Request for changing card status with card id: {}", cardId);
        cardService.changeCardStatus(cardId, cardStatusDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Sends {@link NewCardRequestDto} to AbsService to create new card object
     * @param dto {@link NewCardRequestDto}
     */
    @Override
    public ResponseEntity<Void> createNewCard(NewCardRequestDto dto) {
        kafkaProducer.sendNewCardRequestDto(dto);
        return ResponseEntity.ok().build();
    }

    /**
     * End-point that gets an account number by card number,
     * using {@link AccountService#getAccountByAccountNumber(String)}.
     * After that converting {@link Account}
     * to {@link AccountNumberDto} with {@link AccountMapper#accountToAccountNumberDto(Account)}
     * @param cardNumber card number from request parameters
     * @return {@link AccountNumberDto} with account number
     */
    @Override
    public ResponseEntity<AccountNumberDto> getAccountNumber(String cardNumber) {
        log.info("Request for account with card number: {}", cardNumber);
        AccountNumberDto accountNumberDto =
                accountMapper.accountToAccountNumberDto(accountService.getAccountByCardNumber(cardNumber));
        log.info("Responding with AccountNumberDto with card number: {}", cardNumber);
        return ResponseEntity.ok(accountNumberDto);
    }
}
