package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.auth.AuthService;
import com.cespi.capacitacion.backend.dto.NumberPlateCreationDTO;
import com.cespi.capacitacion.backend.dto.NumberPlateListResponseDTO;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.BadFormatNumberPlateException;
import com.cespi.capacitacion.backend.exception.ResourceNotFoundException;
import com.cespi.capacitacion.backend.repository.NumberPlateRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class NumberPlateServiceImpl implements NumberPlateService {

    private final NumberPlateRepository numberPlateRepository;
    private final UserService userService;
    private final AuthService authService;

    public NumberPlateServiceImpl(NumberPlateRepository numberPlateRepository, UserService userService,
                                  AuthService authService) {
        this.numberPlateRepository = numberPlateRepository;
        this.userService = userService;
        this.authService = authService;
    }

    public NumberPlateListResponseDTO getNumberPlatesOfUser() {
        User user = authService.getUser();
        return new NumberPlateListResponseDTO(user.getAllNumberPlatesStrings());
    }

    @Transactional
    public NumberPlateCreationDTO saveNumberPlate(String number) {
        String sanitizedNumber = sanitizeNumberPlate(number);
        validFormatOfNumberPlate(sanitizedNumber);
        User user = authService.getUser();
        NumberPlate numberPlate;
        try {
            numberPlate = this.findByNumber(sanitizedNumber);
        }
        catch (ResourceNotFoundException e) {
            numberPlate = new NumberPlate(sanitizedNumber);
        }
        user.addNumberPlate(numberPlate);
        userService.save(user);
        return new NumberPlateCreationDTO(sanitizedNumber);
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
