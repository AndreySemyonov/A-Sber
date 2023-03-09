package ru.astondevs.asber.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.astondevs.asber.userservice.dto.FingerprintDto;
import ru.astondevs.asber.userservice.entity.Fingerprint;

/**
 * Mapper that converts entity {@link Fingerprint} to data transfer object and vice versa.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FingerprintMapper {
    /**
     * Method that converts fingerptint from fingerprint dto.
     *
     * @param fingerprintDto {@link FingerprintDto}
     * @return {@link Fingerprint}
     */
    @Mapping(source = "fingerprint", target = "fingerprint")
    Fingerprint fingerprintFromFingerprintDto(FingerprintDto fingerprintDto);
}
