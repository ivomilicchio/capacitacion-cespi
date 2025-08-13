package com.cespi.capacitacion.backend.repository;


import com.cespi.capacitacion.backend.entity.BalanceTopUp;
import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long> {


    @Query("""
    SELECT p
    FROM CurrentAccount c
    JOIN c.transactions t
    JOIN TREAT(t AS ParkingSession) p
    WHERE c.id = :currentAccountId
     AND p.endTime IS NULL
    """)
    Optional<ParkingSession> findByCurrentAccountIdAndEndTimeIsNull(@Param("currentAccountId") Long currentAccountId);

    @Query("""
    SELECT p
    FROM CurrentAccount c
    JOIN c.transactions t
    JOIN TREAT(t AS ParkingSession) p
    WHERE c.id = :currentAccountId
    AND p.endTime IS NOT NULL
    """)
    List<ParkingSession> findAllByCurrentAccountIdAndEndTimeNotNull(@Param("currentAccountId") Long currentAccountId);

    @Query("""
    SELECT b
    FROM CurrentAccount c
    JOIN c.transactions t
    JOIN TREAT(t AS BalanceTopUp) b
    WHERE c.id = :currentAccountId
    AND b.type = :type
    """)
    List<BalanceTopUp> findAllByCurrentAccountIdAndType(Long currentAccountId, TransactionType type);
}
