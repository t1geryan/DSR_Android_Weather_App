package com.example.weatherapp.presentation.ui.location_addition_wizard.location_addition_map_screen.state

import android.os.Parcelable
import com.example.weatherapp.domain.models.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationAdditionState(
    val latLng: LatLng,
    val hasResult: Boolean = false,
    val enteredLocationName: String = "",
    val shouldMoveCameraPosition: Boolean = true,
    val currentMapZoom: Float = INITIAL_MAP_ZOOM,
) : Parcelable {
    companion object {
        private const val INITIAL_MAP_ZOOM = 8.0f
    }
}