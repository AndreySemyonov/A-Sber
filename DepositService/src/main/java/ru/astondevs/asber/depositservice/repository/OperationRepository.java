package ru.astondevs.asber.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.astondevs.asber.depositservice.entity.Operation;

import java.util.UUID;

/**
 * Repository that stores {@link Operation} entities.
 */
@Repository
public interface OperationRepository extends JpaRepository<Operation, UUID> {
}
