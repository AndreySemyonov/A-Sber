package ru.astondevs.asber.creditservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.creditservice.dto.CreditPaymentScheduleDto;
import ru.astondevs.asber.creditservice.dto.PaymentScheduleDto;
import ru.astondevs.asber.creditservice.entity.Account;
import ru.astondevs.asber.creditservice.entity.Agreement;
import ru.astondevs.asber.creditservice.entity.PaymentSchedule;

import java.util.List;

/**
 * Mapper that converts entity {@link PaymentSchedule} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentScheduleMapper {

    /**
     * Method that converts payment schedule to payment schedule dto.
     *
     * @param paymentSchedule {@link PaymentSchedule}
     * @return {@link PaymentScheduleDto}
     */
    @Mapping(target = "paymentDate", source = "paymentDate")
    @Mapping(target = "paymentPrincipal", source = "principal")
    @Mapping(target = "paymentInterest", source = "interest")
    PaymentScheduleDto toPaymentScheduleDto(PaymentSchedule paymentSchedule);

    /**
     * Method that converts list of payment schedules to list of payment schedules dto.
     *
     * @param paymentScheduleList {@link List} of {@link PaymentSchedule}
     * @return {@link List} of {@link PaymentScheduleDto}
     */
    List<PaymentScheduleDto> toPaymentScheduleDtoList(List<PaymentSchedule> paymentScheduleList);

    /**
     * Method that converts list of payment schedules dto, account, agreement to credit payment
     * schedule dto.
     *
     * @param payments  {@link List} of {@link PaymentScheduleDto}
     * @param account   {@link Account}
     * @param agreement {@link Agreement}
     * @return {@link CreditPaymentScheduleDto}
     */
    @Mapping(target = "accountNumber", source = "account.accountNumber")
    @Mapping(target = "agreementId", source = "agreement.id")
    @Mapping(target = "principalDebt", source = "account.principalDebt")
    @Mapping(target = "interestDebt", source = "account.interestDebt")
    CreditPaymentScheduleDto toCreditPaymentScheduleDto(
        List<PaymentScheduleDto> payments,
        Account account,
        Agreement agreement
    );

}
