package com.cespi.capacitacion.backend.repository;

import com.cespi.capacitacion.backend.entity.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSessionRepository  extends JpaRepository<ParkingSession, Long> {

//    @Query("SELECT p FROM ParkingSession p JOIN CurrentAccount c WHERE c.id = :currentAccountId AND p.endTime IS NULL")
//    Optional<ParkingSession> findByCurrentAccountIdAndEndTimeIsNull(@Param("currentAccountId") Long currentAccountId);
//
//    @Query("SELECT p FROM ParkingSession p JOIN CurrentAccount c WHERE c.id = :currentAccountId AND p.endTime IS NOT NULL")
//    List<ParkingSession> findAllByCurrentAccountIdAndEndTimeNotNull(@Param("currentAccountId") Long currentAccountId);

    Optional<ParkingSession> findByNumberPlateIdAndEndTimeIsNull(Long numberPlateId);

}
