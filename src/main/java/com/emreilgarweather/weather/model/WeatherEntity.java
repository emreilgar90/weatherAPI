package com.emreilgarweather.weather.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;


import java.time.LocalDateTime;

@Entity
public class WeatherEntity {

    @Id
    @GeneratedValue(generator= "UUID")
    @GenericGenerator(name= "UUID",strategy="org.hibernate.id.UUIDGenerator")
    private String id;
    private String requestCityName;
    private String cityName;
    private String country;
    private Integer temperature;
    private LocalDateTime updateTime;
    private LocalDateTime responseLocalTime;

    public WeatherEntity() {
    }

    public WeatherEntity(String id, String requestCityName, String cityName, String country, Integer temperature, LocalDateTime updateTime, LocalDateTime responseLocalTime) {
        this.id = id;
        this.requestCityName = requestCityName;
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.updateTime = updateTime;
        this.responseLocalTime = responseLocalTime;
    }
    public WeatherEntity( String requestCityName, String cityName, String country, Integer temperature, LocalDateTime updateTime, LocalDateTime responseLocalTime) {
        this.requestCityName = requestCityName;
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.updateTime = updateTime;
        this.responseLocalTime = responseLocalTime;
    }

    public String getId() {
        return id;
    }

    public String getRequestCityName() {
        return requestCityName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public LocalDateTime getResponseLocalTime() {
        return responseLocalTime;
    }
}
