package com.emreilgarweather.weather.service;

import com.emreilgarweather.weather.constants.Constants;
import com.emreilgarweather.weather.dto.WeatherDto;
import com.emreilgarweather.weather.dto.WeatherResponse;
import com.emreilgarweather.weather.model.WeatherEntity;
import com.emreilgarweather.weather.repository.WeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.emreilgarweather.weather.constants.Constants.API_URL;


@Service
public class WeatherService {

    //https://api.weatherstack.com/current?access_key=5024d2c94f576e2c9163f75f22c08b1e&query=london
   // private static final String API_URL = "https://api.weatherstack.com/current?access_key=5024d2c94f576e2c9163f75f22c08b1e&query=";
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();  //json'ı nesneye çevirmek için kullanılıyor.
    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {

        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    public WeatherDto getWeatherByCityName(String city){
        Optional<WeatherEntity>weatherEntityOptional = weatherRepository.findFirstByRequestedCityNameByUpdatedTimeDesc(city);
        return weatherEntityOptional.map(weather ->{
            if(weather.getUpdateTime().isBefore(LocalDateTime.now().minusSeconds(30))){
                return WeatherDto.convert(getWeatherFromWeatherStack(city));
            }
            return WeatherDto.convert(weather);
        }).orElseGet(()->WeatherDto.convert(getWeatherFromWeatherStack(city)));
    }
    private WeatherEntity getWeatherFromWeatherStack(String city) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(API_URL + city, String.class);

        WeatherResponse weatherResponse;
        try {
            weatherResponse = objectMapper.readValue(responseEntity.getBody(), WeatherResponse.class);
            return saveWeatherEntity(city, weatherResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    private String getWeatherStackUrl(String city){  //apı url string'ini döndüren metot yazdım.
        // "https://api.weatherstack.com/current?access_key=5024d2c94f576e2c9163f75f22c08b1e&query="
        return API_URL+ Constants.ACCESS_KEY_PARAM + Constants.API_KEY + Constants.QUERY_KEY_PARAM +city ;
    }
    private WeatherEntity saveWeatherEntity(String city,WeatherResponse weatherResponse){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM ");
        WeatherEntity weatherEntity =new WeatherEntity(
                city,
                weatherResponse.location().name(),
                weatherResponse.location().country(),
                weatherResponse.current().temperature(),
                LocalDateTime.now(),
                LocalDateTime.parse(weatherResponse.location().localtime(),dateTimeFormatter)
        );
        return weatherRepository.save(weatherEntity);

    }
}
