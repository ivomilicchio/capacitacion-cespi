package com.cespi.capacitacion.backend.util;

import com.cespi.capacitacion.backend.exception.BadFormatNumberPlateException;
import com.cespi.capacitacion.backend.exception.BadFormatPhoneNumberException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  final class ValidationUtils {

    private ValidationUtils() {

    }

    public static String sanitizePhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("[\\s-]", "");
    }

    public static void validFormatOfPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("[0-9]{10}");
        Matcher matcher = pattern.matcher(phoneNumber);

        if  (!matcher.matches()) {
            throw new BadFormatPhoneNumberException();
        }
    }

    public static String sanitizeNumberPlate(String number) {
        return number.toUpperCase().replaceAll("[\\s-]", "");
    }

    public static void validFormatOfNumberPlate(String number) {
        Pattern pattern1 = Pattern.compile("[A-Z]{2}[0-9]{3}[A-Z]{2}");
        Pattern pattern2 = Pattern.compile("[A-Z]{3}[0-9]{3}");

        Matcher matcher1 = pattern1.matcher(number);
        Matcher matcher2 = pattern2.matcher(number);

        if (!(matcher1.matches() || matcher2.matches())) {
            throw new BadFormatNumberPlateException();
        }
    }


}
