package com.example.weatherapp.utils.geocoder

import android.location.Address
import android.location.Geocoder

typealias AddressesCallback = (List<Address>?) -> Unit

interface GeocoderWrapper {

    /**
     * Wrapper for simple geocoding with [Geocoder]. Invokes [callback] on the found list of [Address]
     * @param locationName a user-supplied description of a location
     * @param maxResultsCount – max number of results to return. Smaller numbers (1 to 5) are recommended
     * @param callback a callback for receiving results
     * @see AddressesCallback
     */
    fun getCoordinatesFromLocationName(
        locationName: String,
        maxResultsCount: Int,
        callback: AddressesCallback
    )
}