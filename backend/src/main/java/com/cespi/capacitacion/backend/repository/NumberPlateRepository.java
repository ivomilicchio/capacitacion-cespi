package com.cespi.capacitacion.backend.repository;

import com.cespi.capacitacion.backend.entity.NumberPlate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NumberPlateRepository extends JpaRepository<NumberPlate, Long> {

    Optional<NumberPlate> findByNumber(String number);

}
