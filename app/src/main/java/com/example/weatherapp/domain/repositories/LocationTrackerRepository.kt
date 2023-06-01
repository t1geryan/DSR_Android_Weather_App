package com.example.weatherapp.domain.repositories

import com.example.weatherapp.domain.AppException
import com.example.weatherapp.domain.GpsException
import com.example.weatherapp.domain.PermissionException
import com.example.weatherapp.domain.models.LatLng

typealias CurrentLocationCallback = (LatLng) -> Unit

interface LocationTrackerRepository {

    /**
     * A function that calls [onCallback] with the found current location
     * @throws GpsException
     * @throws PermissionException
     * @throws AppException
     */
    fun getCurrentLocation(onCallback: CurrentLocationCallback)
}