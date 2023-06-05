package com.example.weatherapp.utils.location

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CurrentLocationGettingAbilityCheckerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CurrentLocationGettingAbilityChecker {

    override fun checkGpsEnabled(): Boolean {
        val locationManager = context.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        return locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun checkPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}