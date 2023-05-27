package com.example.weatherapp.presentation.contract

import com.example.weatherapp.domain.models.AppUnitsSystem

interface UnitsSystemApi {

    fun getCurrentUnitsSystem(): AppUnitsSystem

    fun updateCurrentUnitsSystem(unitsSystem: AppUnitsSystem)
}