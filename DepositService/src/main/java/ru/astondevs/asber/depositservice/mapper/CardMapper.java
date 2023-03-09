package ru.astondevs.asber.depositservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.depositservice.dto.NewCardResponseDto;
import ru.astondevs.asber.depositservice.entity.Card;

/**
 *  Mapper that converts entity {@link Card} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {

    /**
     * Method that converts new card response dto to card.
     * @param dto {@link NewCardResponseDto} with information about new card
     * @return {@link Card}
     */
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "cardProductId", ignore = true)
    Card cardFromNewCardResponseDto(NewCardResponseDto dto);
}
