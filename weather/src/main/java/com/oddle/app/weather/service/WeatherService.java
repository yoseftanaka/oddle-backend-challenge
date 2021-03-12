package com.oddle.app.weather.service;

import java.util.Map;

public interface WeatherService {
    Map<String, Object> searchTodayWeather(String cityName);
    void saveWeatherData(String cityId) throws Exception;
    Map<String, Object> getHistoricalWeather(Double longitude, Double latitude);
    void deleteWeatherData(String cityId);
    void updateWeatherData(Long id, Map<String, Object> data) throws Exception;
}
