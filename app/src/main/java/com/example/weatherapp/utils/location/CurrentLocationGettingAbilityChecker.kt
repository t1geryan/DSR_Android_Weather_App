package com.example.weatherapp.utils.location

interface CurrentLocationGettingAbilityChecker {

    fun checkGpsEnabled(): Boolean

    fun checkPermissionsGranted(): Boolean
}