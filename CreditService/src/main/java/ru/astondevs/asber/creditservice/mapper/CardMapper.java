package ru.astondevs.asber.creditservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.creditservice.dto.CardResponseDto;
import ru.astondevs.asber.creditservice.dto.CreditCardInfoResponseDto;
import ru.astondevs.asber.creditservice.dto.CreditResponseDto;
import ru.astondevs.asber.creditservice.entity.Card;

import java.util.List;

/**
 * Mapper that converts entity {@link Card} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {

    /**
     * Method that converts card to card info response dto.
     *
     * @param card {@link Card}
     * @return {@link CreditCardInfoResponseDto}
     */
    @Mapping(target = "accountNumber", source = "card.account.accountNumber")
    @Mapping(target = "name", source = "card.account.credit.creditOrder.product.name")
    @Mapping(target = "principalDebt", source = "card.account.principalDebt")
    @Mapping(target = "creditLimit", source = "card.account.credit.creditLimit")
    @Mapping(target = "currencyCode", source = "card.account.credit.currencyCode")
    @Mapping(target = "terminationDate", source = "card.account.credit.agreement.terminationDate")
    CreditCardInfoResponseDto cardToCreditCardInfoResponseDto(Card card);

    /**
     * Method that converts card to credit response dto.
     *
     * @param card {@link Card}
     * @return {@link CreditResponseDto}
     */
    @Mapping(target = "name", source = "card.account.credit.creditOrder.product.name")
    @Mapping(target = "creditCurrencyCode", source = "card.account.credit.currencyCode")
    @Mapping(target = "agreementDate", source = "card.account.credit.agreement.agreementDate")
    @Mapping(target = "agreementId", source = "card.account.credit.agreement.id")
    @Mapping(target = "agreementNumber", source = "card.account.credit.agreement.agreementNumber")
    @Mapping(target = "accountNumber", source = "card.account.accountNumber")
    @Mapping(target = "cardId", source = "card.id")
    @Mapping(target = "cardNumber", source = "card.cardNumber")
    @Mapping(target = "balance", source = "card.balance")
    @Mapping(target = "accountCurrencyCode", source = "card.account.currencyCode")
    @Mapping(target = "creditLimit", source = "card.account.credit.creditLimit")
    @Mapping(target = "interestRate", source = "card.account.credit.interestRate")
    @Mapping(target = "principalDebt", source = "card.account.principalDebt")
    @Mapping(target = "interestDebt", source = "card.account.interestDebt")
    @Mapping(target = "paymentDate", source = "card.account.paymentSchedule.paymentDate")
    @Mapping(target = "paymentPrincipal", source = "card.account.paymentSchedule.principal")
    @Mapping(target = "paymentInterest", source = "card.account.paymentSchedule.interest")
    CreditResponseDto cardToCreditResponseDto(Card card);

    /**
     * Method that converts card to card response dto.
     *
     * @param card {@link Card}
     * @return {@link CardResponseDto}o
     */
    @Mapping(target = "cardId", source = "id")
    @Mapping(target = "cardNumber", source = "cardNumber")
    @Mapping(target = "balance", source = "balance")
    @Mapping(target = "accountCurrencyCode", source = "card.account.currencyCode")
    CardResponseDto cardToCardResponseDto(Card card);

    /**
     * Method that converts list of cards to list of card response dto.
     *
     * @param cards {@link List} of {@link Card}
     * @return {@link List} of {@link CardResponseDto}
     */
    List<CardResponseDto> cardListToCardResponseDtoList(List<Card> cards);
}
