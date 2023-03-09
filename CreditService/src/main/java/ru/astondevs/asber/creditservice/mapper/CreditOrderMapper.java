package ru.astondevs.asber.creditservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.creditservice.dto.CreditOrderResponseDto;
import ru.astondevs.asber.creditservice.dto.NewCreditOrderRequestDto;
import ru.astondevs.asber.creditservice.dto.NewCreditOrderResponseDto;
import ru.astondevs.asber.creditservice.entity.CreditOrder;
import ru.astondevs.asber.creditservice.util.MapperUtil;

import java.util.List;
import java.util.UUID;

/**
 * Mapper that converts entity {@link CreditOrder} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {MapperUtil.class})
public interface CreditOrderMapper {

    /**
     * Method that converts credit order to credit order response dto.
     *
     * @param creditOrder {@link CreditOrder}
     * @return {@link CreditOrderResponseDto}
     */
    @Mapping(target = "productId", source = "creditOrder.product.id")
    CreditOrderResponseDto creditOrderToCreditOrderResponseDto(CreditOrder creditOrder);

    /**
     * Method that converts list of credit orders to list of credit orders response dto.
     *
     * @param creditOrders {@link List} of {@link CreditOrder}
     * @return {@link List} of {@link CreditOrderResponseDto}
     */
    List<CreditOrderResponseDto> creditOrderListToCreditOrderResponseDtoList(
        List<CreditOrder> creditOrders);

    @Mapping(target = "product", source = "dto.productId", qualifiedByName = { "HelperClass",
            "convertProductIDToProduct" })
    @Mapping(target = "periodMonths", source = "dto.periodMonths", qualifiedByName = { "HelperClass",
            "convertNumberToInteger" })
    @Mapping(target = "amount", source = "dto.amount", qualifiedByName = { "HelperClass",
            "convertNumberToBigDecimal" })
    @Mapping(target = "monthlyIncome", source = "dto.monthlyIncome", qualifiedByName = { "HelperClass",
            "convertNumberToBigDecimal" })
    @Mapping(target = "monthlyExpenditure", source = "dto.monthlyExpenditure", qualifiedByName = { "HelperClass",
            "convertNumberToBigDecimal" })
    @Mapping(target = "creationDate", source = "dto.creationDate", qualifiedByName = { "HelperClass",
            "convertCreationDateToLocalDate" })
    @Mapping(target = "number", source = "dto.number", qualifiedByName = { "HelperClass",
            "setNumber" })
    @Mapping(target = "clientId", source = "clientId")
    CreditOrder toCreditOrder(NewCreditOrderRequestDto dto, UUID clientId);

    @Mapping(target = "productId", source = "product.id")
    NewCreditOrderResponseDto toNewCreditOrderResponseDto(CreditOrder dto);
}
