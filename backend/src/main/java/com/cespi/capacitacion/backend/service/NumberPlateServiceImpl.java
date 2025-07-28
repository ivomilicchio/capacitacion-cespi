package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.dto.NumberPlateCreation;
import com.cespi.capacitacion.backend.dto.NumberPlateListResponse;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.BadFormatNumberPlateException;
import com.cespi.capacitacion.backend.exception.ResourceNotFoundException;
import com.cespi.capacitacion.backend.jwt.JwtService;
import com.cespi.capacitacion.backend.repository.NumberPlateRepository;
import com.cespi.capacitacion.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class NumberPlateServiceImpl implements NumberPlateService {

    private final NumberPlateRepository numberPlateRepository;
    private final UserService userService;

    public NumberPlateServiceImpl(NumberPlateRepository numberPlateRepository, UserService userService) {
        this.numberPlateRepository = numberPlateRepository;
        this.userService = userService;
    }

    @Transactional
    public NumberPlateListResponse getNumberPlatesOfUser(String authHeader) {
        User user = userService.getUserFromAuthHeader(authHeader);
        return new NumberPlateListResponse(user.getAllNumberPlatesStrings());
    }

    @Transactional
    public NumberPlateCreation saveNumberPlate(String number, String authHeader) {
        String sanitizedNumber = sanitizeNumberPlate(number);
        validFormatOfNumberPlate(sanitizedNumber);
        User user = userService.getUserFromAuthHeader(authHeader);
        NumberPlate numberPlate;
        try {
            numberPlate = this.findByNumber(sanitizedNumber);
        }
        catch (ResourceNotFoundException e) {
            numberPlate = new NumberPlate(sanitizedNumber);
        }
        user.addNumberPlate(numberPlate);
        userService.save(user);
        return new NumberPlateCreation(sanitizedNumber);
    }

    public NumberPlate findByNumber(String number) {
        return this.numberPlateRepository.findByNumber(number).orElseThrow(() ->
                new ResourceNotFoundException("Patente"));
    }

    private String sanitizeNumberPlate(String number) {
        return number.toUpperCase().replaceAll("[\\s-]", "");
    }

    private void validFormatOfNumberPlate(String number) {
        Pattern pattern1 = Pattern.compile("[A-Z]{2}[0-9]{3}[A-Z]{2}");
        Pattern pattern2 = Pattern.compile("[A-Z]{3}[0-9]{3}");

        Matcher matcher1 = pattern1.matcher(number);
        Matcher matcher2 = pattern2.matcher(number);

        if (!(matcher1.matches() || matcher2.matches())) {
            throw new BadFormatNumberPlateException();
        }
    }
}
