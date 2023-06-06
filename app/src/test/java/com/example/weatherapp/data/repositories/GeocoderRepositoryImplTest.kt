package com.example.weatherapp.data.repositories

import android.location.Address
import com.example.weatherapp.data.mappers.geocode.AddressToGeocodingResultMapper
import com.example.weatherapp.domain.AppException
import com.example.weatherapp.domain.ConnectionException
import com.example.weatherapp.domain.GeocoderMissingException
import com.example.weatherapp.domain.repositories.GeocodingCallback
import com.example.weatherapp.testutils.arranged
import com.example.weatherapp.testutils.createGeocodingResult
import com.example.weatherapp.utils.geocoder.GeocoderWrapper
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.isNull
import java.io.IOException

class GeocoderRepositoryImplTest {

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
        every { addressToGeocodingResultMapper.map(ADDRESS) } returns GEOCODING_RESULT
    }

    @Test
    fun getCoordinatedByLocationNameInvokesCallbackWithGeocodingResult() {
        every { geocoderWrapper.getCoordinatesFromLocationName(any(), any(), any()) } answers {
            geocodingCallback(
                addressToGeocodingResultMapper.map(ADDRESS)
            )
        }

        repositoryImpl.getCoordinatesByLocationName(LOCATION_NAME, geocodingCallback)

        verify(exactly = 1) {
            addressToGeocodingResultMapper.map(any())
            geocodingCallback(GEOCODING_RESULT)
        }
        confirmVerified(geocodingCallback, addressToGeocodingResultMapper)
    }

    @Test
    fun getCoordinatedByIncorrectLocationNameInvokesCallbackWithNull() {
        every { geocoderWrapper.getCoordinatesFromLocationName(any(), any(), any()) } answers {
            geocodingCallback(isNull())
        }

        repositoryImpl.getCoordinatesByLocationName(LOCATION_NAME, geocodingCallback)

        verify(exactly = 1) {
            geocodingCallback(isNull())
        }
        confirmVerified(geocodingCallback)
    }

    @Test(expected = ConnectionException::class)
    fun getCoordinateByLocationNameWithMissingNetworkConnectionThrowsConnectionException() {
        every {
            geocoderWrapper.getCoordinatesFromLocationName(
                any(),
                any(),
                any()
            )
        } throws IOException()

        repositoryImpl.getCoordinatesByLocationName(LOCATION_NAME, geocodingCallback)

        arranged()
    }

    @Test(expected = GeocoderMissingException::class)
    fun getCoordinateByLocationNameWithMissingGeocoderThrowsGeocoderMissingException() {
        every {
            geocoderWrapper.getCoordinatesFromLocationName(
                any(),
                any(),
                any()
            )
        } throws GeocoderMissingException()

        repositoryImpl.getCoordinatesByLocationName(LOCATION_NAME, geocodingCallback)

        arranged()
    }

    @Test(expected = AppException::class)
    fun getCoordinateByLocationNameWithAnyExceptionThrowsAppException() {
        every {
            geocoderWrapper.getCoordinatesFromLocationName(
                any(),
                any(),
                any()
            )
        } throws RuntimeException()

        repositoryImpl.getCoordinatesByLocationName(LOCATION_NAME, geocodingCallback)

        arranged()
    }

    companion object {
        private const val LOCATION_NAME = "Moscow"
        private const val COUNTRY_NAME = "Russia"

        private val GEOCODING_RESULT =
            createGeocodingResult(locationName = LOCATION_NAME, countryName = COUNTRY_NAME)
        private val ADDRESS = mockk<Address>(relaxed = true)
    }

}