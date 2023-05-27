package com.example.weatherapp.data.mappers.geocode

import android.location.Address
import com.example.weatherapp.domain.models.GeocodingResult
import com.example.weatherapp.domain.models.LatLng
import com.example.weatherapp.utils.Mapper
import javax.inject.Inject

/**
 * Interface for mapping [Address] -> [GeocodingResult]
 * @see [Mapper]
 */
interface AddressToGeocodingResultMapper : Mapper<Address, GeocodingResult>

class AddressToGeocodingResultMapperImpl @Inject constructor() : AddressToGeocodingResultMapper {

    /**
     * Maps [Address] to [GeocodingResult]
     */
    override fun map(valueToMap: Address): GeocodingResult = with(valueToMap) {
        GeocodingResult(
            LatLng(latitude, longitude),
            featureName,
            countryName
        )
    }
}

