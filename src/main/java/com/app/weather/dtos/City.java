package com.app.weather.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class City {

    private int id;
    private String name;
    private String country;
    private int population;
    private int timezone;
    private int sunrise;
    private int sunset;
    private Coord coord;

}