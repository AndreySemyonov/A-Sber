package ru.astondevs.asber.creditservice.util;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.astondevs.asber.creditservice.entity.Product;
import ru.astondevs.asber.creditservice.mapper.CreditOrderMapper;
import ru.astondevs.asber.creditservice.service.ProductService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Helper class that is used in {@link CreditOrderMapper}
 */
@Component
@Named("HelperClass")
@RequiredArgsConstructor
public class MapperUtil {

    private final ProductService productService;

    /**
     * Method that converts number to integer.
     *
     * @param number - Number of {@link Number} type.
     * @return value of number but in integer type.
     */
    @Named("convertNumberToInteger")
    public Integer convertNumberToInteger(Number number) {
        return number.intValue();
    }

    /**
     * Method that converts number to big decimal.
     *
     * @param number - Number of {@link Number} type.
     * @return value of number but in big decimal type.
     */
    @Named("convertNumberToBigDecimal")
    public BigDecimal convertNumberToBigDecimal(Number number) {
        return new BigDecimal(number.intValue());
    }

    /**
     * Method that helps to get product by id
     *
     * @param ID - id of product
     * @return Product
     */
    @Named("convertProductIDToProduct")
    public Product convertProductIDToProduct(Number ID) {
        return productService.getProductByID((Integer) ID);
    }

    @Named("convertCreationDateToLocalDate")
    public LocalDate convertCreationDateToLocalDate(String creationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        return LocalDate.parse(creationDate, formatter);
    }

    @Named("setNumber")
    public String setNumber(String number) {
        return number == null ? "" : number;
    }
}
