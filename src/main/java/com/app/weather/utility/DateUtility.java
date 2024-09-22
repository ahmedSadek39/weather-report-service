package com.app.weather.utility;

import com.app.weather.dtos.LocalDateRange;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtility {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateRange validateDates(String startDate, String endDate) {
        try {
            LocalDate start = (startDate != null) ? LocalDate.parse(startDate, DATE_FORMATTER) : LocalDate.now();
            LocalDate end = (endDate != null) ? LocalDate.parse(endDate, DATE_FORMATTER) : LocalDate.now().plusDays(1);
            return new LocalDateRange(start, end);
        } catch (Exception e){
            throw new RuntimeException("Invalid parsing dates");
        }

    }

}
