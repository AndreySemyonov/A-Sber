package ru.astondevs.asber.creditservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.creditservice.dto.ProductDto;
import ru.astondevs.asber.creditservice.entity.Product;
import ru.astondevs.asber.creditservice.mapper.ProductMapper;
import ru.astondevs.asber.creditservice.service.ProductService;

import java.util.List;

/**
 * Controller that handles requests to {@link ProductService} and return response using
 * {@link ProductMapper}.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api("Operations with credit products")
@RequestMapping("/api/v1/credit-products")
public class CreditProductsController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    /**
     * End-point that gets products by active product, using
     * {@link ProductService#getActiveProducts()}. After this converting list of products to list of
     * product dto, using {@link ProductMapper#productListToProductDtoList(List)}.
     *
     * @return {@link List} of {@link ProductDto} with active credit products
     */
    @GetMapping
    @ApiOperation(value = "Get all active credit products",
        authorizations = {@Authorization(value = "Authorization")})
    public List<ProductDto> getActiveCreditProducts() {
        log.info("Request for active credit products");
        List<Product> activeProducts = productService.getActiveProducts();
        log.info("Responding with active products list, size: {}", activeProducts.size());
        return productMapper.productListToProductDtoList(activeProducts);
    }
}
