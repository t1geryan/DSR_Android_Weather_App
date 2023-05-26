package com.example.weatherapp.domain.repositories

import com.example.weatherapp.domain.models.LatLng

typealias GeocodingCallback = (LatLng) -> Unit

interface GeocoderRepository {

    suspend fun getCoordinatesByLocationName(locationName: String, callback: GeocodingCallback)
}