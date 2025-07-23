package com.cespi.capacitacion.backend.repository;

import com.cespi.capacitacion.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneNumber(String phoneNumber);
    @Query("SELECT n.number FROM User u JOIN u.numberPlates n WHERE u.id = :id")
    List<String>getAllNumberPlatesByUserId(@Param("id") Long id);


}
