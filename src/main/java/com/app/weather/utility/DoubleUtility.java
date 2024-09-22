package com.app.weather.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleUtility {

    public static double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
