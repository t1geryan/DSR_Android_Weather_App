package com.example.weatherapp.data.repositories

import com.example.weatherapp.data.mappers.geocode.AddressToGeocodingResultMapper
import com.example.weatherapp.domain.repositories.GeocodingCallback
import com.example.weatherapp.testutils.createGeocodingResult
import com.example.weatherapp.utils.geocoder.GeocoderWrapper
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GeocoderRepositoryImplTest {

    // TODO: write tests

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var geocoderWrapper: GeocoderWrapper

    @MockK
    lateinit var addressToGeocodingResultMapper: AddressToGeocodingResultMapper

    @RelaxedMockK
    lateinit var geocodingCallback: GeocodingCallback

    private lateinit var repositoryImpl: GeocoderRepositoryImpl

    @Before
    fun setUp() {
        repositoryImpl = GeocoderRepositoryImpl(geocoderWrapper, addressToGeocodingResultMapper)
    }

    @Test
    fun getCoordinatedByLocationNameInvokesCallbackWithGeocodingResult() {

        repositoryImpl.getCoordinatesByLocationName(LOCATION_NAME, geocodingCallback)

        verify(exactly = 1) {
            addressToGeocodingResultMapper.map(any())
            geocodingCallback(GEOCODING_RESULT)
        }
        confirmVerified(geocodingCallback, addressToGeocodingResultMapper)
    }

    @Test
    fun getCoordinatedByIncorrectLocationNameInvokesCallbackWithNull() {

        repositoryImpl.getCoordinatesByLocationName(LOCATION_NAME, geocodingCallback)

        verify(exactly = 1) {
            addressToGeocodingResultMapper.map(any())
            geocodingCallback(isNull())
        }
        confirmVerified(geocodingCallback, addressToGeocodingResultMapper)
    }

    companion object {
        private const val LOCATION_NAME = "Moscow"
        private const val COUNTRY_NAME = "Russia"

        private val GEOCODING_RESULT =
            createGeocodingResult(locationName = LOCATION_NAME, countryName = COUNTRY_NAME)
    }

}