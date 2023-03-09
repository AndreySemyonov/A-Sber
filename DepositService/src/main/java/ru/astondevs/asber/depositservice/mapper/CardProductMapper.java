package ru.astondevs.asber.depositservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.depositservice.dto.CardProductResponseDto;
import ru.astondevs.asber.depositservice.entity.CardProduct;

import java.util.List;

/**
 * Mapper that converts entity {@link CardProduct} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardProductMapper {
    /**
     * Method that converts list of card products to list of card product response dto.
     * @param cardProduct {@link List} of {@link CardProduct}
     * @return {@link List} of {@link CardProductResponseDto}
     */
    List<CardProductResponseDto> cardProductListToCardProductResponseDtoList(List<CardProduct> cardProduct);
}
