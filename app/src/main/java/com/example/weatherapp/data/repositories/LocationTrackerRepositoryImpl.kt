package com.example.weatherapp.data.repositories

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.weatherapp.domain.AppException
import com.example.weatherapp.domain.GpsException
import com.example.weatherapp.domain.PermissionException
import com.example.weatherapp.domain.models.LatLng
import com.example.weatherapp.domain.repositories.CurrentLocationCallback
import com.example.weatherapp.domain.repositories.LocationTrackerRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationTrackerRepositoryImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
) : LocationTrackerRepository {

    /**
     * @throws GpsException
     * @throws PermissionException
     * @throws AppException
     * @see LocationTrackerRepository.getCurrentLocation
     */
    override fun getCurrentLocation(onCallback: CurrentLocationCallback) {
        val locationManager = context.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isGpsEnabled) {
            Log.d("AAA", "NO GPS")
            throw GpsException()
        }

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!hasAccessCoarseLocationPermission)
            throw PermissionException()

        try {
            fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener { location: Location? ->
                if (location != null)
                    onCallback(LatLng(location.latitude, location.longitude))
            }
        } catch (e: Exception) {
            throw AppException(e.message)
        }
    }
}