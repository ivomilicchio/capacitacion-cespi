package com.cespi.capacitacion.backend.service;

import com.cespi.capacitacion.backend.entity.NumberPlate;
import com.cespi.capacitacion.backend.repository.NumberPlateRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NumberPlateServiceImpl implements NumberPlateService {

    private final NumberPlateRepository numberPlateRepository;

    public NumberPlateServiceImpl(NumberPlateRepository numberPlateRepository) {
        this.numberPlateRepository = numberPlateRepository;
    }

    @Transactional
    public NumberPlate save(String number) {
        String sanitizedNumber = sanitizeNumberPlate(number);
        if (!validFormat(sanitizedNumber)) {
            return null; //MANEJAR EXCEPCIONES ACA
        }
        NumberPlate numberPlate = new NumberPlate(sanitizedNumber);
        return numberPlateRepository.save(numberPlate);
    }

    private String sanitizeNumberPlate(String number) {
        return number.toUpperCase().replaceAll("[\\s-]", "");
    }

    private boolean validFormat(String number) {
        Pattern pattern1 = Pattern.compile("[A-Z]{2}[0-9]{3}[A-Z]{2}");
        Pattern pattern2 = Pattern.compile("[A-Z]{3}[0-9]{3}");

        Matcher matcher1 = pattern1.matcher(number);
        Matcher matcher2 = pattern2.matcher(number);

        return matcher1.matches() || matcher2.matches();
    }
}
