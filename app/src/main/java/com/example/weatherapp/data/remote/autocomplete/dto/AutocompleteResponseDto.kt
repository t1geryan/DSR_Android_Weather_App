package com.example.weatherapp.data.remote.autocomplete.dto

import com.example.weatherapp.data.remote.weather.dto.Coordinates
import com.google.gson.annotations.SerializedName

data class AutocompleteResponseDto(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("country_name") val countryName: String,
    @SerializedName("state_code") val stateCode: String?,
    @SerializedName("coordinated") val coordinates: Coordinates,
    @SerializedName("index_strings") val indexString: List<String>,
    @SerializedName("weight ") val weight: Long,
    @SerializedName("cases") val cases: Map<String, String>?,
    @SerializedName("country_cases") val countryCases: Map<String, String>?,
    @SerializedName("main_airport_name") val mainAirportName: String?,
)