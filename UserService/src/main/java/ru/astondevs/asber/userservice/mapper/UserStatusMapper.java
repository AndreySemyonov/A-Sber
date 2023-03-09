package ru.astondevs.asber.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.userservice.dto.UserStatusRegistrationDto;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.enums.ClientStatus;

import java.util.UUID;

/**
 * Mapper that converts entity {@link Client} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserStatusMapper {
    /**
     * Method that converts register not client dto to client.
     *
     * @param clientStatus {@link ClientStatus}
     * @return {@link UserStatusRegistrationDto}
     */
    @Mapping(target = "mobilePhone", source = "mobilePhone")
    @Mapping(target = "clientStatus", source = "clientStatus")
    @Mapping(target = "clientId", source = "clientId")
    UserStatusRegistrationDto toUserStatusRegistrationDto(
            String mobilePhone,
            ClientStatus clientStatus,
            UUID clientId
    );
}
