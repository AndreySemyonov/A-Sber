package ru.astondevs.asber.depositservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.depositservice.dto.ProductResponseDto;
import ru.astondevs.asber.depositservice.entity.Product;

import java.util.List;

/**
 * Mapper that converts entity {@link Product} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    /**
     * Method that converts list of products to list of product response dto.
     *
     * @param product {@link List} of {@link Product}
     * @return {@link List} of {@link ProductResponseDto} with deposit products
     */
    List<ProductResponseDto> productListToProductResponseDtoList(List<Product> product);
}
