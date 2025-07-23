package com.cespi.capacitacion.backend.repository;

import com.cespi.capacitacion.backend.entity.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ParkingSessionRepository  extends JpaRepository<ParkingSession, Long> {

    Optional<ParkingSession> findByCurrentAccountIdAndEndTimeIsNull(Long currentAccountId);
    Optional<ParkingSession> findByNumberPlateIdAndEndTimeIsNull(Long numberPlateId);

}
