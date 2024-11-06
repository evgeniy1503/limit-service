package ru.prokhorov.limitservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.prokhorov.limitservice.entity.Limit;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * LimitRepository.
 *
 * @author Evgeniy_Prokhorov
 */
@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {

    Optional<Limit> getLimitByUserId(Long userId);

    @Transactional
    @Query("UPDATE Limit l SET l.amount = :sumLimit")
    @Modifying(clearAutomatically = true)
    void updateLimit(@Param("sumLimit") BigDecimal sumLimit);
}
