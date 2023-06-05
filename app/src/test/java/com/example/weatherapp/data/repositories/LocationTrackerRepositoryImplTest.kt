package com.example.weatherapp.data.repositories

import com.example.weatherapp.domain.GpsException
import com.example.weatherapp.domain.PermissionException
import com.example.weatherapp.domain.models.LatLng
import com.example.weatherapp.domain.repositories.CurrentLocationCallback
import com.example.weatherapp.testutils.asserted
import com.example.weatherapp.utils.location.CurrentLocationGettingAbilityChecker
import com.google.android.gms.location.FusedLocationProviderClient
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

class LocationTrackerRepositoryImplTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var currentLocationGettingAbilityChecker: CurrentLocationGettingAbilityChecker

    @MockK
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @RelaxedMockK
    lateinit var callback: CurrentLocationCallback

    private lateinit var repositoryImpl: LocationTrackerRepositoryImpl

    @Before
    fun setUp() {
        repositoryImpl = LocationTrackerRepositoryImpl(
            fusedLocationProviderClient,
            currentLocationGettingAbilityChecker
        )
        every {
            fusedLocationProviderClient.getCurrentLocation(
                ofType(Int::class),
                any()
            )
        } answers {
            callback(latLng)
            mockk(relaxed = true)
        }
    }

    @Test
    fun gettingCurrentLocationWithEnabledGpsAndGrantedPermissionsInvokesCallbackWithLatLng() {
        every { currentLocationGettingAbilityChecker.checkGpsEnabled() } returns true
        every { currentLocationGettingAbilityChecker.checkPermissionsGranted() } returns true

        repositoryImpl.getCurrentLocation(callback)

        verify(exactly = 1) {
            callback(latLng)
        }
        confirmVerified(callback)
    }

    @Test(expected = GpsException::class)
    fun gettingCurrentLocationWithDisabledGpsAndGrantedPermissionsThrowsGpsException() {
        every { currentLocationGettingAbilityChecker.checkGpsEnabled() } returns false
        every { currentLocationGettingAbilityChecker.checkPermissionsGranted() } returns true

        repositoryImpl.getCurrentLocation(callback)

        asserted()
    }

    @Test(expected = PermissionException::class)
    fun gettingCurrentLocationWithEnabledGpsAndDeniedPermissionsThrowsPermissionException() {
        every { currentLocationGettingAbilityChecker.checkGpsEnabled() } returns true
        every { currentLocationGettingAbilityChecker.checkPermissionsGranted() } returns false

        repositoryImpl.getCurrentLocation(callback)

        asserted()
    }

    @Test(expected = GpsException::class)
    fun gettingCurrentLocationWithDisabledGpsAndDeniedPermissionsThrowsGpsException() {
        every { currentLocationGettingAbilityChecker.checkGpsEnabled() } returns false
        every { currentLocationGettingAbilityChecker.checkPermissionsGranted() } returns false

        repositoryImpl.getCurrentLocation(callback)

        asserted()
    }

    companion object {
        private val latLng = LatLng(15.0f, 23.0f)
    }
}