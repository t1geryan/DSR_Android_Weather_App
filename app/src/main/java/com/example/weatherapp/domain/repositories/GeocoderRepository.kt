package com.example.weatherapp.domain.repositories

import com.example.weatherapp.domain.models.GeocodingResult

typealias GeocodingCallback = (GeocodingResult?) -> Unit

interface GeocoderRepository {

    suspend fun getCoordinatesByLocationName(locationName: String, callback: GeocodingCallback)
}