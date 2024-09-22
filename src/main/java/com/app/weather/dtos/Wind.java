package com.app.weather.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wind {

    private double speed;
    private int deg;
    private int gust;

}
