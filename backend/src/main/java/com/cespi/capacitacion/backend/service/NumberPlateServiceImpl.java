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
}
