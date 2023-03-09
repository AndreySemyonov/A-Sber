package ru.astondevs.asber.creditservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.creditservice.dto.CreditsForClientCredit;
import ru.astondevs.asber.creditservice.entity.Account;

import java.util.List;

/**
 * Mapper that converts entity {@link Account} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {

    /**
     * Method that converts account to credits for client credit.
     *
     * @param account {@link Account}
     * @return {@link CreditsForClientCredit} with client credits information
     */
    @Mapping(target = "creditId", source = "account.credit.id")
    @Mapping(target = "name", source = "account.credit.creditOrder.product.name")
    @Mapping(target = "principalDebt", source = "account.principalDebt")
    @Mapping(target = "creditLimit", source = "account.credit.creditLimit")
    @Mapping(target = "currencyCode", source = "account.credit.currencyCode")
    @Mapping(target = "terminationDate", source = "account.credit.agreement.terminationDate")
    CreditsForClientCredit creditsToCreditsForClientCredit(Account account);

    /**
     * Method that converts list of accounts to list of credits for client credit.
     *
     * @param accounts {@link List} of {@link Account}
     * @return {@link List} of {@link CreditsForClientCredit} with client credits information
     */
    List<CreditsForClientCredit> creditsListToCreditsForClientCreditList(List<Account> accounts);

}
