package com.app.weather.utility;

public class StringUtility {

    public static String capitalizeFirstChar(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

}
