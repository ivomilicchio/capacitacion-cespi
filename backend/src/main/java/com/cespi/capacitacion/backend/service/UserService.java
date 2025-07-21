package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.User;

import java.util.List;

public interface UserService {

    User save(String phoneNumber, String password);
    List<String> getNumberPlatesOfUser(String token);
    NumberPlate saveNumberPlate(String number, String token);

}
