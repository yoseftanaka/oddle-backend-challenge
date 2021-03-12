package com.oddle.app.weather.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oddle.app.weather.entity.Weather;
import com.oddle.app.weather.feignclient.OpenWeatherMapFeignClient;
import com.oddle.app.weather.repository.WeatherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WeatherServiceImplTest {

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Mock
    private OpenWeatherMapFeignClient openWeatherMapFeignClient;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private WeatherRepository weatherRepository;

    @Captor
    private ArgumentCaptor<Weather> weatherArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(objectMapper, weatherRepository, openWeatherMapFeignClient);
    }

    @Test
    public void searchTodayWeatherTest() throws Exception {
        weatherService.searchTodayWeather("cityName");
        verify(openWeatherMapFeignClient).getCurrentWeather(anyString(), any());
    }

    @Test
    public void saveWeatherDataTest() throws Exception {
        when(openWeatherMapFeignClient.getCurrentCityWeather(anyString(), any()))
                .thenReturn(new Object());
        when(objectMapper.writeValueAsString(any())).thenReturn("JSON_STRING");

        weatherService.saveWeatherData("cityId");

        verify(openWeatherMapFeignClient).getCurrentCityWeather(anyString(), any());
        verify(objectMapper).writeValueAsString(any());
        verify(weatherRepository).save(weatherArgumentCaptor.capture());

        assertEquals(weatherArgumentCaptor.getValue().getCityId(), "cityId");
        assertEquals(weatherArgumentCaptor.getValue().getJson(), "JSON_STRING");
    }

    @Test
    public void deleteWeatherDataTest_ListIsNotEmpty() throws Exception {
        when(weatherRepository.findByCityId(anyString()))
                .thenReturn(Collections.singletonList(new Weather()));

        weatherService.deleteWeatherData("cityId");

        verify(weatherRepository).findByCityId(anyString());
        verify(weatherRepository).deleteAll(anyCollection());
    }

    @Test
    public void deleteWeatherDataTest_ListIsEmpty() throws Exception {
        when(weatherRepository.findByCityId(anyString()))
                .thenReturn(Collections.emptyList());

        weatherService.deleteWeatherData("cityId");

        verify(weatherRepository).findByCityId(anyString());
    }

    @Test
    public void updateWeatherData_DataExist() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("id", 123);

        when(weatherRepository.findById(anyLong())).thenReturn(Optional.of(new Weather()));
        when(objectMapper.writeValueAsString(any())).thenReturn("JSON_STRING");

        weatherService.updateWeatherData(1L, data);

        verify(weatherRepository).findById(anyLong());
        verify(objectMapper).writeValueAsString(any());
        verify(weatherRepository).save(weatherArgumentCaptor.capture());

        assertEquals(weatherArgumentCaptor.getValue().getJson(), "JSON_STRING");
        assertEquals(weatherArgumentCaptor.getValue().getCityId(), "123");
    }

    @Test()
    public void updateWeatherData_DataNotExist() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("id", 123);

        when(weatherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            weatherService.updateWeatherData(1L, data);
        });

        verify(weatherRepository).findById(anyLong());

    }
}
