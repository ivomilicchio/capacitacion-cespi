package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.NumberPlateCreation;
import com.cespi.capacitacion.backend.dto.NumberPlateListResponse;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.BadFormatNumberPlateException;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.repository.NumberPlateRepository;
import com.cespi.capacitacion.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class NumberPlateServiceImpl implements NumberPlateService {

    private final NumberPlateRepository numberPlateRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public NumberPlateServiceImpl(NumberPlateRepository numberPlateRepository, UserRepository userRepository,
                                  JwtService jwtService) {
        this.numberPlateRepository = numberPlateRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public NumberPlateListResponse getNumberPlatesOfUser(String token) {
        User user = userRepository.findByPhoneNumber(jwtService.getPhoneNumberFromToken(token)).orElseThrow();
        return new NumberPlateListResponse(user.getNumberPlates().stream().map(NumberPlate::getNumber).toList());

    }

    @Transactional
    public NumberPlateCreation saveNumberPlate(String number, String token) {
        String sanitizedNumber = sanitizeNumberPlate(number);
        if (!validFormatOfNumberPlate(sanitizedNumber)) {
            throw new BadFormatNumberPlateException();
        }
        String phoneNumber = jwtService.getPhoneNumberFromToken(token);
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        
        NumberPlate numberPlate = new NumberPlate(sanitizedNumber);
        user.addNumberPlate(numberPlate);
        userRepository.save(user);
        return new NumberPlateCreation(sanitizedNumber);
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
