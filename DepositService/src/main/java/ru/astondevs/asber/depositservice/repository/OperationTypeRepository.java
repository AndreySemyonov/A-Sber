package ru.astondevs.asber.depositservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.astondevs.asber.depositservice.entity.OperationType;

/**
 * Repository that stores {@link OperationType} entities.
 */
@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Integer> {
}
