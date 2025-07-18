package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public User save(String phoneNumber, String password) {
        String sanitizedPhoneNumber = sanitizePhoneNumber(phoneNumber);
        if (!validFormat(sanitizedPhoneNumber)) {
            return null; //MANEJAR EXCEPCIONES ACA
        }
        User user = new User(sanitizedPhoneNumber, password);
        return userRepository.save(user);
    }

    @Override
    public List<String> getNumberPlatesOfUser(String token) {
        String phoneNumber = jwtService.getPhoneNumberFromToken(token);
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        return userRepository.getAllNumberPlatesByUserId(user.getId());
    }

    private String sanitizePhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("[\\s-]", "");
    }

    private boolean validFormat(String phoneNumber) {
        Pattern pattern = Pattern.compile("[0-9]{10}");
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }
}
