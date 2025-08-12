package com.cespi.capacitacion.backend.util;

public class StringUtils {

    public static String sanitizePhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("[\\s-]", "");
    }

    public static String sanitizeNumberPlate(String number) {
        return number.toUpperCase().replaceAll("[\\s-]", "");
    }
}
