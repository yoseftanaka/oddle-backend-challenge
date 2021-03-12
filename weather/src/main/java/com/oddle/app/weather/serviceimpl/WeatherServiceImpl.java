package com.oddle.app.weather.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oddle.app.weather.entity.Weather;
import com.oddle.app.weather.feignclient.OpenWeatherMapFeignClient;
import com.oddle.app.weather.repository.WeatherRepository;
import com.oddle.app.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService {
    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Autowired
    private OpenWeatherMapFeignClient openWeatherMapFeignClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    public Map<String, Object> searchTodayWeather(String cityName) {
        return openWeatherMapFeignClient.getCurrentWeather(cityName, apiKey);
    }

    @Override
    @Transactional
    public void saveWeatherData(String cityId) throws Exception {
        Object response = openWeatherMapFeignClient.getCurrentCityWeather(cityId, apiKey);
        String json = objectMapper.writeValueAsString(response);
        Weather weather = Weather.builder()
                .cityId(cityId)
                .json(json)
                .build();
        weatherRepository.save(weather);
    }

    @Override
    public Map<String, Object> getHistoricalWeather(Double longitude, Double latitude) {
        return openWeatherMapFeignClient.getHistoricalWeather(longitude, latitude, apiKey);
    }

    @Override
    @Transactional
    public void deleteWeatherData(String cityId) {
        List<Weather> weatherList = weatherRepository.findByCityId(cityId);
        if (!weatherList.isEmpty()) {
            weatherRepository.deleteAll(weatherList);
        }
    }

    @Override
    @Transactional
    public void updateWeatherData(Long id, Map<String, Object> data) throws Exception {
        Optional<Weather> optionalWeather = weatherRepository.findById(id);
        if (optionalWeather.isPresent()) {
            Weather weather = optionalWeather.get();
            weather.setCityId(String.valueOf(data.get("id")));
            weather.setJson(objectMapper.writeValueAsString(data));
            weatherRepository.save(weather);
        } else {
            throw new Exception();
        }
    }
}
