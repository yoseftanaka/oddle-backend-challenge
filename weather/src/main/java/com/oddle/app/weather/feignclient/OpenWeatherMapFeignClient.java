package com.oddle.app.weather.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "OpenWeatherApi", url = "http://api.openweathermap.org/data/2.5")
public interface OpenWeatherMapFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/weather")
    Map<String, Object> getCurrentWeather(@RequestParam String q, @RequestParam String appId);

    @RequestMapping(method = RequestMethod.GET, value = "/weather")
    Object getCurrentCityWeather(@RequestParam String id, @RequestParam String appId);

    @RequestMapping(method = RequestMethod.GET, value = "/onecall")
    Map<String, Object> getHistoricalWeather(@RequestParam Double lon,
                                             @RequestParam Double lat,
                                             @RequestParam String appId);
}
