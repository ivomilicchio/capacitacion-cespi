package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.entity.User;

public interface UserService {

    User save(String phoneNumber, String password);

}
