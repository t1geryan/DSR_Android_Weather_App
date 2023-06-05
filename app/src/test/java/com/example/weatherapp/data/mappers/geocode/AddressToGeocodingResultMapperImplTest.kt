package com.example.weatherapp.data.mappers.geocode

import com.example.weatherapp.testutils.createAddress
import com.example.weatherapp.testutils.createGeocodingResult
import org.junit.Assert
import org.junit.Test

class AddressToGeocodingResultMapperImplTest {

    @Test
    fun mapAddressToGeocodingResultReturnsGeocodingResult() {
        val mapperImpl = AddressToGeocodingResultMapperImpl()
        val expectedGeocodingResult =
            createGeocodingResult(locationName = "city name", countryName = "country name")
        val address = createAddress(featureName = "city name", countryName = "country name")

        val actualGeocodingResult = mapperImpl.map(address)

        Assert.assertEquals(expectedGeocodingResult, actualGeocodingResult)
    }
}