package com.app.weather.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class LocalDateRange {

    private final LocalDate start;
    private final LocalDate end;

}
