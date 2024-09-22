package com.app.weather.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WeatherList {

    private int dt;
    @JsonProperty(value = "main")
    private WeatherMain weatherMain;
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private int visibility;
    private int pop;
    @JsonProperty(value = "sys")
    private WeatherSys weatherSys;
    @JsonProperty("dt_txt")
    private String dtTxt;

}
