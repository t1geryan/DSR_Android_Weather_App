package com.example.weatherapp.data.repositories

import android.annotation.SuppressLint
import android.location.Location
import com.example.weatherapp.domain.AppException
import com.example.weatherapp.domain.GpsException
import com.example.weatherapp.domain.PermissionException
import com.example.weatherapp.domain.models.LatLng
import com.example.weatherapp.domain.repositories.CurrentLocationCallback
import com.example.weatherapp.domain.repositories.LocationTrackerRepository
import com.example.weatherapp.utils.location.CurrentLocationGettingAbilityChecker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import javax.inject.Inject

class LocationTrackerRepositoryImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val currentLocationGettingAbilityChecker: CurrentLocationGettingAbilityChecker,
) : LocationTrackerRepository {

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(onCallback: CurrentLocationCallback) {
        if (!currentLocationGettingAbilityChecker.checkGpsEnabled())
            throw GpsException()

        if (!currentLocationGettingAbilityChecker.checkPermissionsGranted())
            throw PermissionException()

        try {
            fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener { location: Location? ->
                if (location != null)
                    onCallback(LatLng(location.latitude.toFloat(), location.longitude.toFloat()))
            }
        } catch (e: Exception) {
            throw AppException(e.message)
        }
    }
}