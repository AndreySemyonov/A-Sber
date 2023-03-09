package ru.astondevs.asber.depositservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.depositservice.dto.CardProductResponseDto;
import ru.astondevs.asber.depositservice.dto.ProductResponseDto;
import ru.astondevs.asber.depositservice.mapper.CardProductMapper;
import ru.astondevs.asber.depositservice.mapper.ProductMapper;
import ru.astondevs.asber.depositservice.service.CardProductService;
import ru.astondevs.asber.depositservice.service.ProductService;

import java.util.List;

/**
 * Controller that handles requests to {@link ProductService}, {@link CardProductService}
 * and return response using {@link ProductMapper}, {@link CardProductMapper}
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class InfoController implements InfoControllerApi {

    private final ProductService productService;
    private final CardProductService cardProductService;
    private final ProductMapper productMapper;
    private final CardProductMapper cardProductMapper;

    /**
     * End-point that gets list of products, using {@link ProductService#getProducts()}.
     * After this converting list of products to list of product response dto, using
     * {@link ProductMapper#productListToProductResponseDtoList(List)}.
     * @return {@link List} of {@link ProductResponseDto} with all information about credit products
     */
    @Override
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        log.info("Request for list of ProductResponseDto");
        List<ProductResponseDto> productResponseDtos = productMapper.productListToProductResponseDtoList(
                productService.getProducts());
        log.info("Responding with list of ProductResponseDto");
        return ResponseEntity.ok(productResponseDtos);
    }

    /**
     * End-point that gets list of card products, using {@link CardProductService#getCardProducts()}.
     * After this converting list of card products to list of card product response dto, using
     * {@link CardProductMapper#cardProductListToCardProductResponseDtoList(List)}
     * @return {@link List} of {@link CardProductResponseDto} with all information about card products
     */
    @Override
    public ResponseEntity<List<CardProductResponseDto>> getCardProducts() {
        log.info("Request for list of CardProductResponseDto");
        List<CardProductResponseDto> cardProductResponseDtos = cardProductMapper.cardProductListToCardProductResponseDtoList(
                cardProductService.getCardProducts());
        log.info("Responding with list of CardProductResponseDto");
        return ResponseEntity.ok(cardProductResponseDtos);
    }
}
