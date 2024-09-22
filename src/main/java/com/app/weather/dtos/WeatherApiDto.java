package com.app.weather.dtos;

import lombok.Getter;
import lombok.Setter;

import static com.app.weather.utility.DoubleUtility.roundToTwoDecimalPlaces;

@Setter
@Getter
public class WeatherApiDto {

    private double currentTemperature;
    private double minTemperature;
    private double maxTemperature;
    private String status;
    private String dateTime;

    public WeatherApiDto(double currentTemperature, double minTemperature, double maxTemperature, String status, String dateTime) {
        this.currentTemperature = roundToTwoDecimalPlaces(currentTemperature);
        this.minTemperature = roundToTwoDecimalPlaces(minTemperature);
        this.maxTemperature = roundToTwoDecimalPlaces(maxTemperature);
        this.status = status;
        this.dateTime = dateTime;
    }

}
