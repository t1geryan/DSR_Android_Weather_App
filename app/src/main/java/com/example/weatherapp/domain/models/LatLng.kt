package com.example.weatherapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LatLng(
    val latitude: Float,
    val longitude: Float,
) : Parcelable
