package ru.astondevs.asber.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.astondevs.asber.userservice.entity.UserProfile;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository that stores {@link UserProfile} entities.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    /**
     * Method that finds user profile by client id.
     *
     * @param uuid UUID id from {@link UUID}
     * @return {@link UserProfile}
     */
    Optional<UserProfile> findUserProfileByClientId(UUID uuid);

    /**
     * Method that finds user profile by phone number.
     *
     * @return {@link UserProfile}
     */
    @Query(value = """
            select u from Client c inner join Contacts o on c.id = o.client
            inner join UserProfile u on c.id = u.client 
            where o.mobilePhone=:phone""")
    Optional<UserProfile> findUserProfileByPhoneNumber(String phone);

    /**
     * Method that finds user profile by passport number.
     *
     * @return {@link UserProfile}
     */
    @Query(value = """
            select u from Client c inner join PassportData p on c.id = p.client
            inner join UserProfile u on c.id = u.client
            where p.identificationPassportNumber=:passport""")
    Optional<UserProfile> findUserProfileByPassportNumber(String passport);
}
