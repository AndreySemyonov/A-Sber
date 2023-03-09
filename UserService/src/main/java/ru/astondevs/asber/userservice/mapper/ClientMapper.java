package ru.astondevs.asber.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.userservice.dto.RegisterNotClientDto;
import ru.astondevs.asber.userservice.entity.Client;

/**
 * Mapper that converts entity {@link Client} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {
    /**
     * Method that converts register not client dto to client.
     *
     * @param clientDto {@link RegisterNotClientDto}
     * @return {@link Client}
     */
    @Mapping(source = "middleName", target = "surName")
    Client clientFromRegisterNotClientDto(RegisterNotClientDto clientDto);
}
