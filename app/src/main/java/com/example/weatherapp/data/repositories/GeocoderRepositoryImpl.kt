package com.example.weatherapp.data.repositories

import android.location.Address
import com.example.weatherapp.data.mappers.geocode.AddressToGeocodingResultMapper
import com.example.weatherapp.domain.AppException
import com.example.weatherapp.domain.ConnectionException
import com.example.weatherapp.domain.repositories.GeocoderRepository
import com.example.weatherapp.domain.repositories.GeocodingCallback
import com.example.weatherapp.utils.geocoder.GeocoderWrapper
import java.io.IOException
import javax.inject.Inject

class GeocoderRepositoryImpl @Inject constructor(
    private val geocoderWrapper: GeocoderWrapper,
    private val addressToGeocodingResultMapper: AddressToGeocodingResultMapper,
) : GeocoderRepository {

    override fun getCoordinatesByLocationName(
        locationName: String,
        callback: GeocodingCallback
    ) {
        try {
            geocoderWrapper.getCoordinatesFromLocationName(
                locationName,
                MAX_RESULTS_COUNT
            ) { addresses ->
                invokeCallbackOnFirstAddress(addresses, callback)
            }
        } catch (e: IOException) {
            throw ConnectionException(e)
        } catch (e: AppException) {
            throw e
        } catch (e: Exception) {
            throw AppException(e)
        }
    }

    private fun invokeCallbackOnFirstAddress(
        addresses: List<Address>?,
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