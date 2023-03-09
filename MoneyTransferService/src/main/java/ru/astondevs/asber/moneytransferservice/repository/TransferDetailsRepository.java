package ru.astondevs.asber.moneytransferservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.astondevs.asber.moneytransferservice.entity.TransferDetails;

/**
 * Repository that stores {@link TransferDetails} entities.
 */
public interface TransferDetailsRepository extends JpaRepository<TransferDetails, Long> {

    Optional<TransferDetails> findById(Long id);

    /**
     * Method that returns all operatoins for senderId from DB
     *
     * @param senderId {@link UUID}
     * @return list of {@link TransferDetails}
     */
    List<TransferDetails> findAllBySenderIdOrderByStartDateDesc(UUID senderId);

    /**
     * Method that return list of operations with pagination
     * @param senderId sender id
     * @param pageable {@link PageRequest}
     * @return Pagination with operation history
     */
    Page<TransferDetails> findAllBySenderIdOrderByStartDateDesc(UUID senderId, Pageable pageable);
}
