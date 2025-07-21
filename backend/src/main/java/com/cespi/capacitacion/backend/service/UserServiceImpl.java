package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.entity.CurrentAccount;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.ParkingSession;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.repository.CurrentAccountRepository;
import com.cespi.capacitacion.backend.repository.ParkingSessionRepository;
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
    private final ParkingSessionRepository parkingSessionRepository;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService,
                           ParkingSessionRepository parkingSessionRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.parkingSessionRepository = parkingSessionRepository;
    }

    @Transactional
    public User save(String phoneNumber, String password) {
        String sanitizedPhoneNumber = sanitizePhoneNumber(phoneNumber);
        if (!validFormatOfPhoneNumber(sanitizedPhoneNumber)) {
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

    private boolean validFormatOfPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("[0-9]{10}");
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

    @Transactional
    public NumberPlate saveNumberPlate(String number, String token) {
        String sanitizedNumber = sanitizeNumberPlate(number);
        if (!validFormatOfNumberPlate(sanitizedNumber)) {
            return null; //MANEJAR EXCEPCIONES ACA
        }
        String phoneNumber = jwtService.getPhoneNumberFromToken(token);
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        NumberPlate numberPlate = new NumberPlate(sanitizedNumber);
        user.addNumberPlate(numberPlate);
        userRepository.save(user);
        return numberPlate;

    }

    @Override
    public ParkingSession hasSessionStarted(String token) {
        String phoneNumber = jwtService.getPhoneNumberFromToken(token);
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        Long accountId = user.getCurrentAccount().getId();
        System.out.println(parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(accountId));
        return parkingSessionRepository.findByCurrentAccountIdAndEndTimeIsNull(accountId);
    }

    private String sanitizeNumberPlate(String number) {
        return number.toUpperCase().replaceAll("[\\s-]", "");
    }

    private boolean validFormatOfNumberPlate(String number) {
        Pattern pattern1 = Pattern.compile("[A-Z]{2}[0-9]{3}[A-Z]{2}");
        Pattern pattern2 = Pattern.compile("[A-Z]{3}[0-9]{3}");

        Matcher matcher1 = pattern1.matcher(number);
        Matcher matcher2 = pattern2.matcher(number);

        return matcher1.matches() || matcher2.matches();
    }
}
