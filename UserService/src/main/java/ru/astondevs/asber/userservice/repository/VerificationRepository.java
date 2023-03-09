package ru.astondevs.asber.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.astondevs.asber.userservice.entity.Contacts;
import ru.astondevs.asber.userservice.entity.Verification;

/**
 * Repository that stores {@link Verification} entities.
 */
@Repository
public interface VerificationRepository extends JpaRepository<Verification, Integer> {

    /**
     * Method that deletes entry about verification by contacts.
     * @param contacts Contacts from {@link Contacts}
     */
    @Modifying
    @Query(value = """
                delete from Verification v where v.contacts=:contacts
                """)
    void deleteByContacts(Contacts contacts);
}
