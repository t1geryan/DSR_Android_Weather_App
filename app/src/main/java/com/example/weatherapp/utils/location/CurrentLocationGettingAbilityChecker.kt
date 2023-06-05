package com.example.weatherapp.utils.location

interface CurrentLocationGettingAbilityChecker {

    /**
     * Checks if the device's GPS is enabled
     */
    fun checkGpsEnabled(): Boolean

    /**
     * Checks for ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION permissions
     */
    fun checkPermissionsGranted(): Boolean
}