package com.cespi.capacitacion.backend.repository;

import com.cespi.capacitacion.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhoneNumberAndPassword(String phoneNumber, String password);

}
