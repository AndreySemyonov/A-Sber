package ru.astondevs.asber.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.userservice.dto.RegisterNotClientDto;
import ru.astondevs.asber.userservice.entity.UserProfile;

/**
 * Mapper that converts entity {@link UserProfile} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileMapper {
    /**
     * Method that converts register not client dto to client.
     *
     * @param notClientDto {@link RegisterNotClientDto}
     * @return {@link UserProfile}
     */
    @Mapping(source = "password", target = "passwordEncoded")
    UserProfile userProfileFromRegisterNotClientDto(RegisterNotClientDto notClientDto);
}
