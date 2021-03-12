package com.oddle.app.weather.controller;

import com.oddle.app.weather.constant.ApiPath;
import com.oddle.app.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(ApiPath.WEATHER)
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping(value = "/search-today-weather")
    public Map<String, Object> searchTodayWeatherByCity(@RequestParam String cityName) {
        return weatherService.searchTodayWeather(cityName);
    }

    @GetMapping(value = "/save-weather-data")
    public String saveWeatherData(@RequestParam String cityId) {
        try {
            weatherService.saveWeatherData(cityId);
            return "Request Successful";
        } catch (Exception e) {
            return "Request Fail";
        }
    }

    @GetMapping(value = "/get-historical-weather")
    public Map<String, Object> getHistoricalWeather(@RequestParam Double longitude,
                                                    @RequestParam Double latitude) {
        return weatherService.getHistoricalWeather(longitude, latitude);
    }

    @DeleteMapping(value = "/delete-weather-data")
    public String deleteWeather(@RequestParam String cityId) {
        try {
            weatherService.deleteWeatherData(cityId);
            return "Request Successful";
        } catch (Exception e) {
            return "Request Fail";
        }
    }

    @PutMapping(value = "/update-weather-data")
    public String updateWeather(@RequestParam Long id,
                                @RequestBody Map<String, Object> data) {
        try {
            weatherService.updateWeatherData(id, data);
            return "Request Successful";
        } catch (Exception e) {
            return "Request Fail";
        }
    }
}
