package com.example.weatherapp.data.repositories

import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.example.weatherapp.domain.models.LatLng
import com.example.weatherapp.domain.repositories.GeocoderRepository
import com.example.weatherapp.domain.repositories.GeocodingCallback
import javax.inject.Inject

class GeocoderRepositoryImpl @Inject constructor(
    private val geocoder: Geocoder
) : GeocoderRepository {

    @Suppress("DEPRECATION")
    override suspend fun getCoordinatesByLocationName(
        locationName: String,
        callback: GeocodingCallback
    ) {
        if (Geocoder.isPresent()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocationName(
                    locationName,
                    MAX_RESULTS_COUNT,
                ) { addresses ->
                    invokeCallbackOnFirstAddress(addresses, callback)
                }
            } else {
                val addresses = geocoder.getFromLocationName(locationName, MAX_RESULTS_COUNT)
                invokeCallbackOnFirstAddress(addresses, callback)
            }
        }
    }

    private fun invokeCallbackOnFirstAddress(
        addresses: MutableList<Address>?,
        callback: GeocodingCallback
    ) {
        addresses?.firstOrNull()?.let { address ->
            callback(LatLng(address.latitude, address.longitude))

        }
    }

    companion object {
        private const val MAX_RESULTS_COUNT = 1
    }
}