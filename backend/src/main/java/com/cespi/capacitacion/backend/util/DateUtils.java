package com.cespi.capacitacion.backend.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public final class DateUtils {

    public static String getDayOfWeek() {
        return LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }
}
