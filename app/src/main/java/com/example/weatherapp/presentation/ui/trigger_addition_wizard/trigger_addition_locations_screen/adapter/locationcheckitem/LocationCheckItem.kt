package com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_locations_screen.adapter.locationcheckitem

import com.example.weatherapp.domain.models.Location

data class LocationCheckItem(
    val location: Location,
    val isSelected: Boolean,
)