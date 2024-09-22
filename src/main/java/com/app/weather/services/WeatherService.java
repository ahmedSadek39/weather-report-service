package com.app.weather.services;

import com.app.weather.dtos.LocalDateRange;
import com.app.weather.dtos.WeatherApiDto;
import com.app.weather.dtos.WeatherList;
import com.app.weather.dtos.WeatherResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.app.weather.utility.WeatherUtility.convertTemperatureFromKelvinToCelsius;

@Service
public class WeatherService {

    private final WebClient webClient;
    private final String baseUrl = "https://api.openweathermap.org/data/2.5/";
    private final String apiKey;

    public WeatherService(WebClient.Builder builder,  @Value("${weathermap.key}") String apiKey) {
        this.webClient = builder.baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public Mono<WeatherResponseDto> getWeatherData(String city, LocalDateRange dateRange) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("forecast")
                        .queryParam("q", city)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(WeatherResponseDto.class)
                .map(weatherResponse -> {
                    List<WeatherList> filteredWeather = filterWeatherData(weatherResponse, dateRange);
                    weatherResponse.setWeatherList(filteredWeather);
                    return weatherResponse;
                })
                .onErrorMap(e -> new RuntimeException("Error while fetching weather data: " + e.getMessage()));
    }


    public List<WeatherList> filterWeatherData(WeatherResponseDto weatherData, LocalDateRange dateRange) {
        return weatherData.getWeatherList()
                .stream()
                .filter(w -> {
                    LocalDate weatherDate = LocalDate.parse(w.getDtTxt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    return !weatherDate.isBefore(dateRange.getStart()) && !weatherDate.isAfter(dateRange.getEnd());
                })
                .collect(Collectors.toList());
    }

    public List<WeatherApiDto> getWeatherReportDtos(WeatherResponseDto weatherResponse) {
        return weatherResponse.getWeatherList()
                .stream()
                .map(this::mapToWeatherReportDto)
                .collect(Collectors.toList());
    }

    private WeatherApiDto mapToWeatherReportDto(WeatherList weatherList) {

        double currentTempCelsius =  convertTemperatureFromKelvinToCelsius(weatherList.getWeatherMain().getTemp());
        double minTempCelsius = convertTemperatureFromKelvinToCelsius(weatherList.getWeatherMain().getTempMin());
        double maxTempCelsius = convertTemperatureFromKelvinToCelsius(weatherList.getWeatherMain().getTempMax());
        String status = weatherList.getWeather().get(0).getMain();
        String hour = weatherList.getDtTxt();
        return new WeatherApiDto(currentTempCelsius, minTempCelsius, maxTempCelsius, status, hour);
    }

}
