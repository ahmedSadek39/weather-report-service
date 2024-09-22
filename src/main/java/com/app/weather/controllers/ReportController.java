package com.app.weather.controllers;

import com.app.weather.dtos.LocalDateRange;
import com.app.weather.services.PdfService;
import com.app.weather.services.WeatherService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

import static com.app.weather.utility.DateUtility.validateDates;
import static com.app.weather.utility.vaildation.validateWeatherRequests.validateGenerateWeatherReportRequest;
import static com.app.weather.utility.htttp.RequestHandler.handleRequest;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final WeatherService weatherService;
    private final PdfService pdfService;

    public ReportController(WeatherService weatherService, PdfService pdfService) {
        this.weatherService = weatherService;
        this.pdfService = pdfService;
    }

    @GetMapping("/{city}")
    public Mono<ResponseEntity<byte[]>> generateWeatherReportForCity(
            @PathVariable String city,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Authentication authentication) {

        return handleRequest(() -> {
            validateGenerateWeatherReportRequest(authentication, city);
            LocalDateRange dateRange = validateDates(startDate, endDate);
            return weatherService.getWeatherData(city, dateRange)
                    .map(weatherData -> {
                        byte[] pdf = pdfService.generateWeatherReport(weatherData);
                        return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, generatePdfFileName(city))
                                .contentType(MediaType.APPLICATION_PDF)
                                .body(pdf);
                    });
        });
    }

    private String generatePdfFileName(String city){
        // inline; for open pdf in browser page not download directly
        return "inline; attachment; filename=weather-report-" + city + ".pdf";
    }

}