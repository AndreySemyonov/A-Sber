package ru.astondevs.asber.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.userservice.dto.RegisterNotClientDto;
import ru.astondevs.asber.userservice.entity.PassportData;

/**
 * Mapper that converts entity {@link PassportData} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassportMapper {
    /**
     * Method that converts passport data from register not client dto.
     *
     * @param clientDto {@link RegisterNotClientDto}
     * @return {@link PassportData}
     */
    @Mapping(source = "passportNumber", target = "identificationPassportNumber")
    PassportData passportDataFromRegisterNotClientDto(RegisterNotClientDto clientDto);
}
