package com.app.weather.utility;

import static com.app.weather.utility.DoubleUtility.roundToTwoDecimalPlaces;

public class WeatherUtility {

    public static double convertTemperatureFromKelvinToCelsius(double temp){
        return roundToTwoDecimalPlaces(temp - 273.15);
    }

}
