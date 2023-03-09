package ru.astondevs.asber.creditservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.creditservice.dto.CardResponseDto;
import ru.astondevs.asber.creditservice.dto.CreditCardInfoResponseDto;
import ru.astondevs.asber.creditservice.dto.CreditCardStatusDto;
import ru.astondevs.asber.creditservice.entity.Card;
import ru.astondevs.asber.creditservice.mapper.CardMapper;
import ru.astondevs.asber.creditservice.service.CardService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import ru.astondevs.asber.creditservice.util.SensitiveInformationMasker;

/**
 * Controller that handles requests to {@link CardService} and return response using
 * {@link CardMapper}.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api("Operations with credit cards")
@RequestMapping("/api/v1/credit-cards")
public class CreditCardsController {

    private final CardService cardService;
    private final CardMapper cardMapper;

    /**
     * End-point that gets all cards by client id, using
     * {@link CardService#getCardsByClientId(UUID)}. After this converting list of cards to list of
     * card response dto, using {@link CardMapper#cardListToCardResponseDtoList(List)}.
     *
     * @param clientId Client id from request query parameters
     * @return {@link List} of {@link CardResponseDto} with all information about client credit
     * cards
     */
    @GetMapping
    @ApiOperation(value = "Get client's credit cards by client's id",
        authorizations = {@Authorization(value = "Authorization")})
    public List<CardResponseDto> getCardsByClientId(@RequestParam UUID clientId) {
        log.info("Request for cards with client id: {}", clientId.toString());
        List<Card> cards = cardService.getCardsByClientId(clientId);
        log.info("Responding with list of cards with client id: {}, size of list:{}", clientId,
            cards.size());
        return cardMapper.cardListToCardResponseDtoList(cards);
    }

    /**
     * End-point that gets card by card id, using
     * {@link CardService#getCreditCardInfoByCardId(UUID)}. After this converting card to credit
     * card info response dto, using {@link CardMapper#cardToCreditCardInfoResponseDto(Card)}.
     *
     * @param cardId Card id from request uri.
     * @return {@link CreditCardInfoResponseDto} with information about client credit card
     */
    @GetMapping("/{cardId}")
    @ApiOperation(value = "Get credit card's information by card's id",
        authorizations = {@Authorization(value = "Authorization")})
    public CreditCardInfoResponseDto getCreditCardInfoByCardId(@PathVariable UUID cardId) {
        log.info("Request for credit card info with card id: {}", cardId);
        Card creditCardInfo = cardService.getCreditCardInfoByCardId(cardId);
        log.info("Responding for credit card info with card id: {}", creditCardInfo.getId());
        return cardMapper.cardToCreditCardInfoResponseDto(creditCardInfo);
    }

    /**
     * End-point that changes credit card status, getting value from received request body. For
     * sending this into db uses {@link CardService#changeCardStatus(CreditCardStatusDto)}.
     *
     * @param creditCardStatusDto {@link CreditCardStatusDto} from request body with credit card
     *                            status
     */
    @PatchMapping("/active-cards")
    @ApiOperation(value = "Change credit card status",
        authorizations = {@Authorization(value = "Authorization")})
    public void changeCreditCardStatus(
        @RequestBody @Valid CreditCardStatusDto creditCardStatusDto) {
        log.info("Request for changing card status with card number: {}",
            SensitiveInformationMasker.getMaskedString(creditCardStatusDto.getCardNumber()));
        cardService.changeCardStatus(creditCardStatusDto);
        log.info("Successfully changed card status");
    }
}
