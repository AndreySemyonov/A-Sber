package ru.astondevs.asber.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.userservice.dto.RegisterNotClientDto;
import ru.astondevs.asber.userservice.dto.UserNotificationsDto;
import ru.astondevs.asber.userservice.entity.Contacts;

/**
 * Mapper that converts entity {@link Contacts} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContactsMapper {
    /**
     * Method that converts contact from register not client dto.
     *
     * @param clientDto {@link RegisterNotClientDto}
     * @return {@link Contacts}
     */
    Contacts contactsFromRegisterNotClientDto(RegisterNotClientDto clientDto);

    /**
     * Method that converts contact to user notifications dto.
     *
     * @param contact {@link Contacts}
     * @return {@link UserNotificationsDto}
     */
    @Mapping(target = "email", source = "email")
    @Mapping(target = "pushNotification", source = "pushNotificationEnabled")
    @Mapping(target = "smsNotification", source = "smsNotificationEnabled")
    @Mapping(target = "emailSubscription", source = "emailSubscriptionEnabled")
    UserNotificationsDto toUserNotificationsDto(
            Contacts contact
    );
}
