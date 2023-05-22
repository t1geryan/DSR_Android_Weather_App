package com.example.weatherapp.domain.repositories

import com.example.weatherapp.domain.models.LatLng

typealias CurrentLocationCallback = (LatLng) -> Unit

interface LocationTrackerRepository {

    /**
     * A function that calls [onCallback] with the found current location
     */
    fun getCurrentLocation(onCallback: CurrentLocationCallback)
}