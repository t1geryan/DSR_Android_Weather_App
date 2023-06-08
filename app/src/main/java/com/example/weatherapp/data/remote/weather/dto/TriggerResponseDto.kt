package com.example.weatherapp.data.remote.weather.dto

import com.google.gson.annotations.SerializedName

data class TriggerResponseDto(
    @SerializedName("__v") val version: Int,
    @SerializedName("_id") val id: String,
    @SerializedName("alerts") val alerts: Map<String, TriggerResponseAlert>,
    @SerializedName("time_period") val timePeriod: TriggerResponseTimePeriod,
    @SerializedName("conditions") val conditions: List<TriggerResponseCondition>,
    @SerializedName("area") val area: List<TriggerResponseArea>,
)

data class TriggerResponseAlert(
    @SerializedName("conditions") val conditions: List<TriggerResponseAlertCondition>,
    @SerializedName("lat_update") val lastUpdateUnix: Long,
    @SerializedName("date") val dateUnix: Long,
    @SerializedName("coordinates") val coordinates: Coordinates,

    )

data class TriggerResponseAlertCondition(
    @SerializedName("current_value") val currentValue: AlertCurrentValue,
    @SerializedName("condition") val condition: TriggerResponseCondition,
)

data class AlertCurrentValue(
    @SerializedName("min") val min: Double,
    @SerializedName("max") val max: Double,
)

typealias TriggerResponseTimePeriod = TriggerRequestTimePeriod

data class TriggerResponseCondition(
    @SerializedName("name") val name: String,
    @SerializedName("expression") val expression: String,
    @SerializedName("amount") val amount: Int,
    @SerializedName("_id") val id: String,
)

data class TriggerResponseArea(
    @SerializedName("type") val type: String,
    @SerializedName("_id") val id: String,
    @SerializedName("coordinates") val coordinates: List<Coordinates>
)