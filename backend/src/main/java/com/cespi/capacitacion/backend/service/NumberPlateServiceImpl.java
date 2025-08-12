package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.auth.AuthService;
import com.cespi.capacitacion.backend.dto.NumberPlateCreationDTO;
import com.cespi.capacitacion.backend.dto.NumberPlateListResponseDTO;
import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.entity.User;
import com.cespi.capacitacion.backend.exception.ResourceNotFoundException;
import com.cespi.capacitacion.backend.repository.NumberPlateRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static com.cespi.capacitacion.backend.util.ValidationUtils.sanitizeNumberPlate;
import static com.cespi.capacitacion.backend.util.ValidationUtils.validFormatOfNumberPlate;


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

}
