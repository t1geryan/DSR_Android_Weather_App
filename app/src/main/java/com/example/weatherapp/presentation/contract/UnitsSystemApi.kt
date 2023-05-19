package com.example.weatherapp.presentation.contract

import com.example.weatherapp.domain.models.AppUnitsSystem

typealias UnitsSystemUpdateListener = (AppUnitsSystem) -> Unit

interface UnitsSystemApi {

    fun getCurrentUnitsSystem(): AppUnitsSystem

    fun updateCurrentUnitsSystem(unitsSystem: AppUnitsSystem)
}