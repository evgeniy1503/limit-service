package ru.prokhorov.limitservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.prokhorov.limitservice.entity.Operation;

/**
 * Репозиторий для работы с Операциями.
 *
 * @author Evgeniy_Prokhorov
 */
@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
}