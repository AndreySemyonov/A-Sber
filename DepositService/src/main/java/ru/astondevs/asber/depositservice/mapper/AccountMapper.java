package ru.astondevs.asber.depositservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.depositservice.dto.AccountNumberDto;
import ru.astondevs.asber.depositservice.dto.AccountResponseDto;
import ru.astondevs.asber.depositservice.dto.AgreementForClientDeposits;
import ru.astondevs.asber.depositservice.dto.CardForClientDeposits;
import ru.astondevs.asber.depositservice.dto.ClientCardsResponseDto;
import ru.astondevs.asber.depositservice.dto.ClientDepositResponseDto;
import ru.astondevs.asber.depositservice.dto.ClientDepositsResponseDto;
import ru.astondevs.asber.depositservice.dto.ProductForClientDeposits;
import ru.astondevs.asber.depositservice.entity.Account;
import ru.astondevs.asber.depositservice.entity.Agreement;
import ru.astondevs.asber.depositservice.entity.Card;
import ru.astondevs.asber.depositservice.entity.Product;

import java.util.List;
import java.util.Set;

/**
 * Mapper that converts entity {@link Account} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {
    /**
     * Method that converts list of accounts to list of account response dto.
     * @param account {@link List} of {@link Account}
     * @return {@link List} of {@link AccountResponseDto} with account information
     */
    List<AccountResponseDto> accountListToAccountResponseDtoList(List<Account> account);

    /**
     * Method that converts entities card, agreement, product to client deposit response dto.
     * @param card {@link Card}
     * @param agreement {@link Agreement}
     * @param product {@link Product}
     * @return {@link ClientDepositResponseDto} with client deposit information
     */
    ClientDepositResponseDto toClientDepositResponseDto(Card card, Agreement agreement, Product product);

    /**
     * Method that converts set of accounts to set of client deposits response dto.
     * @param account {@link Set} of {@link Account}
     * @return {@link Set} of {@link ClientDepositsResponseDto} with all client deposits information
     */
    Set<ClientDepositsResponseDto> accountSetToClientDepositsResponseDtoSet(Set<Account> account);

    /**
     * Method that converts set of accounts to set of client cards response dto.
     * @param account {@link Set} of {@link Account}
     * @return {@link Set} of {@link ClientCardsResponseDto} with all client cards information
     */
    Set<ClientCardsResponseDto> accountSetToClientCardsResponseDtoSet(Set<Account> account);

    /**
     * Method that converts account to account number response dto.
     * @param account {@link Account}
     * @return {@link AccountNumberDto} with information of account number
     */
    AccountNumberDto accountToAccountNumberDto(Account account);

    /**
     * Method that converts agreement to agreement for client deposits dto.
     * @param agreement {@link Agreement}
     * @return {@link AgreementForClientDeposits}
     */
    @Mapping(target = "agreementId", source = "id")
    @Mapping(target = "product", source = "productId")
    AgreementForClientDeposits agreementToAgreementForClientDeposits(Agreement agreement);

    /**
     * Method that converts set of agreements to set of agreement for client deposits dto.
     * @param set {@link Set} of {@link Agreement}
     * @return {@link Set} of {@link AgreementForClientDeposits}
     */
    Set<AgreementForClientDeposits> agreementSetToAgreementForClientDepositsSet(Set<Agreement> set);

    /**
     * Method that converts card to card for client deposits dto.
     * @param card {@link Card}
     * @return {@link CardForClientDeposits}
     */
    @Mapping(target = "cardId", source = "id")
    CardForClientDeposits cardToCardForClientDeposits(Card card);

    /**
     * Method that converts set of cards to set of card for client deposits dto.
     * @param set {@link Set} of {@link Card}
     * @return {@link Set} of {@link CardForClientDeposits}
     */
    Set<CardForClientDeposits> cardSetToCardForClientDepositsSet(Set<Card> set);

    /**
     * Method that converts product to product for client deposits.
     * @param product {@link Product}
     * @return {@link ProductForClientDeposits}
     */
    @Mapping(target = "productName", source = "name")
    ProductForClientDeposits productToProductForClientDeposits(Product product);
}
