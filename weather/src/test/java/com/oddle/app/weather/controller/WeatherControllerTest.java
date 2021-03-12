package com.oddle.app.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oddle.app.weather.service.WeatherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void searchTodayWeatherByCityTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/weather/search-today-weather")
                .param("cityName", "Medan")
        ).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void saveWeatherDataTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/weather/save-weather-data")
                .param("cityId", "123")
        ).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void getHistoricalWeatherTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/weather/get-historical-weather")
                .param("longitude", "1")
                .param("latitude", "2")
        ).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deleteWeatherTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/weather/delete-weather-data")
                .param("cityId", "123")
        ).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void updateWeatherTest() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("id", 123);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/weather/update-weather-data")
                .param("id", "1")
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .content("{\"coord\":{\"lon\":106.8451,\"lat\":-6.2146},\"weather\":[{\"id\":721,\"main\":\"Haze\",\"description\":\"haze\",\"icon\":\"50d\"}],\"base\":\"stations\",\"main\":{\"temp\":298.24,\"feels_like\":302.49,\"temp_min\":297.15,\"temp_max\":299.15,\"pressure\":1012,\"humidity\":89},\"visibility\":4000,\"wind\":{\"speed\":1.54,\"deg\":240},\"clouds\":{\"all\":20},\"dt\":1615511191,\"sys\":{\"type\":1,\"id\":9383,\"country\":\"ID\",\"sunrise\":1615503462,\"sunset\":1615547240},\"timezone\":25200,\"id\":1642911,\"name\":\"Jakarta\",\"cod\":200}")
        ).andExpect(MockMvcResultMatchers.status().is(200));
    }
}
