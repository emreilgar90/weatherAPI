package com.emreilgarweather.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Location(
        String name,
        String country,
        String region,
        Double lat,
        Double lon,
        @JsonProperty("timezone_id")
        String timezoneId,
        String localtime,
        @JsonProperty("localtime_epoch")//json da localtime olarak yazılmış ondan ötürü böyle yazdık.
        String localtimeEpoch,
        @JsonProperty("utc_offset")
        String utcOffset


) {
}
