package ru.astondevs.asber.moneytransferservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.moneytransferservice.dto.AccountNumberDto;
import ru.astondevs.asber.moneytransferservice.service.CardService;

/**
 * Controller that handles requests to {@link CardService}.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/info")
@RequiredArgsConstructor
public class InfoController implements InfoControllerApi {
    private final CardService cardService;

    /**
     * End point that gets checks is card number valid by returning
     * card holder's account number by using {@link CardService#getAccountNumberIfCardNumberIsPresent(String)}
     * @param cardNumber is a card number from request
     * @return {@link AccountNumberDto} with account number
     */
    @Override
    @GetMapping("/check-card")
    public ResponseEntity<AccountNumberDto> checkCardNumber(@RequestParam String cardNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardService.getAccountNumberIfCardNumberIsPresent(cardNumber));
    }
}
