package com.emreilgarweather.weather.dto;

public record Request(
        String type,   //Request yazılmasının nedeni wearherAPI'sinde json format adında Request olarak geçiyor adı.
        String query,
        String language,
        String unit) {
}
