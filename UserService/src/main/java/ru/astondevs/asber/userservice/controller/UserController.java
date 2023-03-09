package ru.astondevs.asber.userservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.asber.userservice.dto.MobilePhoneDto;
import ru.astondevs.asber.userservice.dto.PassportNumberDto;
import ru.astondevs.asber.userservice.dto.RegisterClientDto;
import ru.astondevs.asber.userservice.dto.RegisterNotClientDto;
import ru.astondevs.asber.userservice.dto.UserStatusRegistrationDto;
import ru.astondevs.asber.userservice.entity.Client;
import ru.astondevs.asber.userservice.entity.Contacts;
import ru.astondevs.asber.userservice.entity.PassportData;
import ru.astondevs.asber.userservice.entity.UserProfile;
import ru.astondevs.asber.userservice.mapper.ClientMapper;
import ru.astondevs.asber.userservice.mapper.ContactsMapper;
import ru.astondevs.asber.userservice.mapper.PassportMapper;
import ru.astondevs.asber.userservice.mapper.UserProfileMapper;
import ru.astondevs.asber.userservice.mapper.UserStatusMapper;
import ru.astondevs.asber.userservice.service.ClientService;
import ru.astondevs.asber.userservice.service.ContactsService;
import ru.astondevs.asber.userservice.service.UserProfileService;
import ru.astondevs.asber.userservice.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

/**
 * Controller that handles requests to {@link UserService}, {@link UserProfileService}, {@link ContactsService}
 * and return response using {@link UserStatusMapper}.
 */
@Api("User controller")
@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ContactsService contactsService;
    private final ClientService clientService;
    private final UserStatusMapper userStatusMapper;
    private final ClientMapper clientMapper;
    private final ContactsMapper contactsMapper;
    private final PassportMapper passportMapper;
    private final UserProfileMapper userProfileMapper;

    /**
     * End-point that creates non-client user, using
     * {@link UserService#registerNotClient(RegisterNotClientDto, Client, Contacts, PassportData, UserProfile)}.
     *
     * @param request dto from request body
     */
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Register not client")
    public void registerNotClient(@Valid @RequestBody RegisterNotClientDto request) {
        log.info("Request for non-client user registration");
        Contacts contact = contactsMapper.contactsFromRegisterNotClientDto(request);
        Client client = clientMapper.clientFromRegisterNotClientDto(request);
        PassportData passportData = passportMapper.passportDataFromRegisterNotClientDto(request);
        UserProfile userProfile = userProfileMapper.userProfileFromRegisterNotClientDto(request);
        userService.registerNotClient(request, client, contact, passportData, userProfile);
        log.info("Created non-client user with id: {}", client.getId());
    }

    /**
     * End-point that creates client user, using {@link UserService#registerClient(RegisterClientDto)}.
     *
     * @param request dto from request body
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Register client")
    public void registerClient(@Valid @RequestBody RegisterClientDto request) {
        log.info("Request for client user registration");
        userService.registerClient(request);
        log.info("Created client user with id: {}", request.getId());
    }

    /**
     * End-point that changes user registration status, using {@link UserService#getUserRegistrationStatusByMobilePhone(String)}.
     *
     * @return {@link UserStatusRegistrationDto}
     */
    @GetMapping("/status")
    @ApiOperation(value = "Change user registration status")
    public UserStatusRegistrationDto getUserRegistrationStatus(@RequestParam @Pattern(regexp = "^\\d{11}$") String mobilePhone) {
        log.info("Request for status by mobilephone: {}", mobilePhone);

        Contacts userContact = userService.getUserRegistrationStatusByMobilePhone(mobilePhone).orElse(null);

        UserStatusRegistrationDto userStatusRegistrationDto = userStatusMapper.toUserStatusRegistrationDto(
                mobilePhone,
                userService.getClientStatus(userContact),
                userService.getClientId(userContact)
        );
        log.info("Return user status registration for id: {}", userStatusRegistrationDto.getClientId());

        return userStatusRegistrationDto;
    }

    /**
     * End-point that gets phone number by passport number, using {@link ContactsService#getPhoneByPassportNumber(String)}.
     *
     * @param passportNumberDto from request body
     * @return {@link String} with passport number
     */
    @PostMapping("/phone")
    @ApiOperation(value = "Get user's phone number by passport number")
    public MobilePhoneDto getPhoneByPassportNumber(@RequestBody @Valid PassportNumberDto passportNumberDto) {
        log.info("Request for phone by passport number: {}", passportNumberDto.getPassportNumber());
        MobilePhoneDto mobilePhoneDto = contactsService.getPhoneByPassportNumber(passportNumberDto.getPassportNumber());
        log.info("Return mobile number for user");
        return mobilePhoneDto;
    }
}
