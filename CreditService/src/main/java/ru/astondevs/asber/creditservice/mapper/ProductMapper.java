package ru.astondevs.asber.creditservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.creditservice.dto.ProductDto;
import ru.astondevs.asber.creditservice.entity.Product;

import java.util.List;

/**
 * Mapper that converts entity {@link Product} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    /**
     * Method that converts product to product dto.
     *
     * @param product {@link Product}
     * @return {@link ProductDto}
     */
    ProductDto productToProductDto(Product product);

    /**
     * Method that converts list of products to list of products dto.
     *
     * @param productList {@link List} of {@link Product}
     * @return {@link List} of {@link ProductDto}
     */
    List<ProductDto> productListToProductDtoList(List<Product> productList);
}
