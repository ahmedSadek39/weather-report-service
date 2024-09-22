package com.app.weather.utility.vaildation;

import org.springframework.security.core.Authentication;

public class validateWeatherRequests {

    public static void validateGenerateWeatherReportRequest(Authentication authentication, String city) {
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }
        validateCity(city);
    }

    public static void validateCity(String city){
        if (city == null || city.isEmpty()) {
            throw new RuntimeException("You must enter city");
        }
    }

}
