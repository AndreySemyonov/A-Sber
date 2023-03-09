package ru.astondevs.asber.creditservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.creditservice.dto.CreditOrderResponseDto;
import ru.astondevs.asber.creditservice.dto.NewCreditOrderRequestDto;
import ru.astondevs.asber.creditservice.dto.NewCreditOrderResponseDto;
import ru.astondevs.asber.creditservice.entity.CreditOrder;
import ru.astondevs.asber.creditservice.mapper.CreditOrderMapper;
import ru.astondevs.asber.creditservice.service.CreditOrderService;

import java.util.List;
import java.util.UUID;

/**
 * Controller that handles requests to {@link CreditOrderService} and return response using
 * {@link CreditOrderMapper}.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api("Operations with credit orders")
@RequestMapping("/api/v1/credit-orders")
public class CreditOrderController {

    private final CreditOrderService creditOrderService;
    private final CreditOrderMapper creditOrderMapper;

    /**
     * End-point that gets all credit orders by client id, using
     * {@link CreditOrderService#getCreditOrdersByClientId(UUID)}. After this converting list of
     * credit orders to list of credit order response dto, using
     * {@link CreditOrderMapper#creditOrderListToCreditOrderResponseDtoList(List)}.
     *
     * @param clientId Client id from request query parameters
     * @return {@link List} of {@link CreditOrderResponseDto} with all client credit orders
     */
    @GetMapping
    @ApiOperation(value = "Get client's credit orders",
            authorizations = {@Authorization(value = "Authorization")})
    public List<CreditOrderResponseDto> getCreditOrdersByClientId(@RequestParam UUID clientId) {
        log.info("Request for credit orders with client id: {}", clientId);
        List<CreditOrder> creditOrderList = creditOrderService.getCreditOrdersByClientId(clientId);
        log.info("Responding with credit order list with client id: {}, list size: {}", clientId,
                creditOrderList.size());
        return creditOrderMapper.creditOrderListToCreditOrderResponseDtoList(creditOrderList);
    }

    /**
     * End-point that make new credit order, using {@link CreditOrderService#getNewCreditOrder(CreditOrder)}.
     * After this converting CreditOrder to New Credit Order Response dto,
     * using {@link CreditOrderMapper#toCreditOrder(dto, clientId)}.
     *
     * @param dto New Credit Order Request Dto from {@link NewCreditOrderRequestDto}.
     * @return {@link NewCreditOrderResponseDto} with information about created credit order
     */
    @PostMapping("/new")
    public NewCreditOrderResponseDto getNewCreditOrder(@RequestParam UUID clientId,
                                                       @RequestBody NewCreditOrderRequestDto dto) {
        log.info("Request for new credit order with product ID: {} and clientId from RequestParam: {}",
                dto.getProductId(), clientId);
        CreditOrder request = creditOrderMapper.toCreditOrder(dto, clientId);
        NewCreditOrderResponseDto response = creditOrderMapper
                .toNewCreditOrderResponseDto(creditOrderService.getNewCreditOrder(request));
        log.info("Request for new credit order with ID: {}", response.getId());
        return response;
    }
}
