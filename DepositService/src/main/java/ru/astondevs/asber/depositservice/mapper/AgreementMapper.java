package ru.astondevs.asber.depositservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.depositservice.dto.AbsDepositAgreementMessageDto;
import ru.astondevs.asber.depositservice.dto.DepositAgreementDto;
import ru.astondevs.asber.depositservice.entity.Account;
import ru.astondevs.asber.depositservice.entity.Agreement;
import ru.astondevs.asber.depositservice.entity.Product;

/**
 *  Mapper that converts entity {@link Agreement} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AgreementMapper {
    /**
     * Method that converts deposit agreement dto to agreement.
     * @param dto {@link DepositAgreementDto} with information about new deposit agreement
     * @return {@link Agreement}
     */
    @Mapping(target = "productId", ignore = true)
    Agreement agreementFromDepositAgreementDto(DepositAgreementDto dto);

    /**
     * Method that converts {@link AbsDepositAgreementMessageDto} to {@link Agreement}
     * except 2 fields: {@link Account} and {@link Product}
     * @param message {@link AbsDepositAgreementMessageDto} with information about new deposit agreement
     * @return {@link Agreement}
     */
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "currentBalance", source = "initialAmount")
    Agreement agreementFromAbsDepositAgreementMessageDto(AbsDepositAgreementMessageDto message);
}
