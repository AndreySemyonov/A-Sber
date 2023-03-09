package ru.astondevs.asber.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.astondevs.asber.userservice.entity.Contacts;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository that stores {@link Contacts} entities.
 */
@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Integer> {
    /**
     * Method that find contact by mobile phone.
     *
     * @return {@link Contacts}
     */
    Optional<Contacts> findContactsByMobilePhone(String phoneNumber);

    /**
     * Method that find contact with verification by mobile phone.
     *
     * @return {@link Contacts}
     */
    @Query(value = "SELECT c from Contacts c LEFT JOIN Verification v ON c.id = v.contacts.id "
            + "WHERE c.mobilePhone = :phoneNumber")
    Optional<Contacts> findContactsWithVerificationByMobilePhone(@Param("phoneNumber") String phoneNumber);

    /**
     * Method that find phone number by passport number.
     *
     * @return {@link Contacts}
     */
    @Query(value = "SELECT c.mobilePhone from Contacts c "
            + "INNER JOIN PassportData p ON c.client.id = p.client.id "
            + "WHERE p.identificationPassportNumber = :passportNumber")
    Optional<String> getPhoneByPassportNumber(@Param("passportNumber") String passportNumber);

    /**
     * Method that find contact by client id.
     *
     * @return {@link Contacts}
     */
    Optional<Contacts> findContactsByClientId(UUID uuid);
}
