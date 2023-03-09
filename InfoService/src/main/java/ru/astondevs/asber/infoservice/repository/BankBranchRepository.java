package ru.astondevs.asber.infoservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.astondevs.asber.infoservice.entity.BankBranch;

import java.util.UUID;

/**
 * Repository that stores {@link BankBranch} entity.
 */
public interface BankBranchRepository extends JpaRepository<BankBranch, UUID> {

    /**
     * Method that return list of bank branches with pagination
     * @param pageable {@link PageRequest}
     * @return Pagination with bank branch
     */

    Page<BankBranch> findAll(Pageable pageable);

}

