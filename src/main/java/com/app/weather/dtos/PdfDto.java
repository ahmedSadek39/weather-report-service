package com.app.weather.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PdfDto {

    private String city;
    private String weatherDescription;
    private Double currentTemp;
    private Double minTemp;
    private Double maxTemp;

}
