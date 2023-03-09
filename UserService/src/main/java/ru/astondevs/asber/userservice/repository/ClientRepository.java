package ru.astondevs.asber.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.astondevs.asber.userservice.entity.Client;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository that stores {@link ClientRepository} entities.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    /**
     * Method that finds client with user profile information.
     *
     * @param id UUID id from {@link UUID}
     * @return {@link Client}
     */
    @Query(value = "SELECT c from Client c LEFT JOIN FETCH c.contacts LEFT JOIN FETCH c.userProfile WHERE c.id = :id")
    Optional<Client> getClientWithContactsAndUserProfile(@Param("id") UUID id);

    /**
     * Method that finds client with existing fingerprint.
     *
     * @param id UUID id from {@link UUID}
     * @return {@link Client}
     */
    @Query(value = "SELECT c from Client c LEFT JOIN FETCH c.fingerprint  WHERE c.id = :id")
    Optional<Client> getClientWithFingerprint(@Param("id") UUID id);
}
