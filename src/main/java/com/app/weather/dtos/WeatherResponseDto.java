package com.app.weather.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponseDto {

    private String cod;
    private Integer message;
    private Integer cnt;
    @JsonProperty(value = "list")
    private List<WeatherList> weatherList;
    private City city;

}
