package com.example.weatherapp.domain.repositories

import com.example.weatherapp.domain.AppException
import com.example.weatherapp.domain.ConnectionException
import com.example.weatherapp.domain.models.GeocodingResult

typealias GeocodingCallback = (GeocodingResult?) -> Unit

interface GeocoderRepository {

    /**
     * Geocoding function
     * @throws ConnectionException
     * @throws AppException
     */
    fun getCoordinatesByLocationName(locationName: String, callback: GeocodingCallback)
}