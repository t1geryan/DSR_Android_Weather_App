package com.example.weatherapp.data.repositories

import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.example.weatherapp.data.mappers.geocode.AddressToGeocodingResultMapper
import com.example.weatherapp.domain.AppException
import com.example.weatherapp.domain.ConnectionException
import com.example.weatherapp.domain.repositories.GeocoderRepository
import com.example.weatherapp.domain.repositories.GeocodingCallback
import java.io.IOException
import javax.inject.Inject

class GeocoderRepositoryImpl @Inject constructor(
    private val geocoder: Geocoder,
    private val addressToGeocodingResultMapper: AddressToGeocodingResultMapper,
) : GeocoderRepository {

    @Suppress("DEPRECATION")
    override fun getCoordinatesByLocationName(
        locationName: String,
        callback: GeocodingCallback
    ) {
        try {
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
        } catch (e: IOException) {
            throw ConnectionException(e)
        } catch (e: Exception) {
            throw AppException(e)
        }
    }

    private fun invokeCallbackOnFirstAddress(
        addresses: MutableList<Address>?,
        callback: GeocodingCallback
    ) {
        // call callback with first address in list or with null if list is empty or null
        callback(
            addresses?.firstOrNull()?.let { address ->
                addressToGeocodingResultMapper.map(address)
            }
        )
    }

    companion object {
        private const val MAX_RESULTS_COUNT = 1
    }
}