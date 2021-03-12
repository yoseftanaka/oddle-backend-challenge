package com.oddle.app.weather.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Weather.WEATHER_TABLE_NAME)
public class Weather {

    public static final String WEATHER_TABLE_NAME = "weather";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cityId;

    @Column(name = "json", columnDefinition="TEXT")
    private String json;
}
