package ru.astondevs.asber.infoservice.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.infoservice.dto.ExchangeRateDto;
import ru.astondevs.asber.infoservice.entity.ExchangeRate;
import ru.astondevs.asber.infoservice.mapper.utils.ExchangeRateMapperHelper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {
    ExchangeRateMapperHelper.class})
public interface ExchangeRateMapper {

    @Mapping(target = "updateAt", source = "updateAt", qualifiedByName = {"toTimestamp"})
    ExchangeRate toExchangeRate(ExchangeRateDto exchangeRateDto);

    @Mapping(target = "updateAt", source = "updateAt", qualifiedByName = {"toLocalDateTime"})
    ExchangeRateDto toExchangeRateDto(ExchangeRate exchangeRate);

    List<ExchangeRateDto> toExchangeRates(List<ExchangeRate> exchangeRates);
}


