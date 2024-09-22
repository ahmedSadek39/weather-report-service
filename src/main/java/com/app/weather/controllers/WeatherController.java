package com.app.weather.controllers;

import com.app.weather.dtos.LocalDateRange;
import com.app.weather.dtos.WeatherApiDto;
import com.app.weather.services.WeatherService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.app.weather.utility.DateUtility.validateDates;
import static com.app.weather.utility.htttp.RequestHandler.handleRequest;
import static com.app.weather.utility.vaildation.validateWeatherRequests.validateCity;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("{city}")
    public Mono<ResponseEntity<List<WeatherApiDto>>> getWeatherForCity(@PathVariable String city,
                                                             @RequestParam(required = false) String startDate,
                                                             @RequestParam(required = false) String endDate) {

        return handleRequest(() -> {
            validateCity(city);
            LocalDateRange dateRange = validateDates(startDate, endDate);

            return weatherService.getWeatherData(city, dateRange)
                    .map(w -> {
                        List<WeatherApiDto> reportDtos = weatherService.getWeatherReportDtos(w);
                        return ResponseEntity.ok(reportDtos);
                    });
        });

    }

}