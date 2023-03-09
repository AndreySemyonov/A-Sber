package ru.astondevs.asber.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.astondevs.asber.userservice.entity.PassportData;

import java.util.Optional;

/**
 * Repository that stores {@link PassportData} entities.
 */
@Repository
public interface PassportDataRepository extends JpaRepository<PassportData, Integer> {
    /**
     * Method that find passport data by passport number.
     *
     * @return {@link PassportData}
     */
    Optional<PassportData> findPassportDataByIdentificationPassportNumber(String passportNumber);
}
