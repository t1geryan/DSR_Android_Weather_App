package com.example.weatherapp.domain.models

data class GeocodingResult(
    val latLng: LatLng,
    val locationName: String,
    val countryName: String,
)