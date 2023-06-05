package com.example.weatherapp.utils.geocoder

import android.location.Geocoder
import android.os.Build
import com.example.weatherapp.domain.AppException
import javax.inject.Inject

class GeocoderWrapperImpl @Inject constructor(
    private val geocoder: Geocoder,
) : GeocoderWrapper {

    @Suppress("DEPRECATION")
    override fun getCoordinatesFromLocationName(
        locationName: String,
        maxResultsCount: Int,
        callback: AddressesCallback
    ) {
        if (Geocoder.isPresent()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocationName(
                    locationName,
                    maxResultsCount,
                ) {
                    callback(it)
                }
            } else {
                val addresses = geocoder.getFromLocationName(
                    locationName,
                    maxResultsCount
                )
                callback(addresses)
            }
        } else
            throw AppException()
    }
}