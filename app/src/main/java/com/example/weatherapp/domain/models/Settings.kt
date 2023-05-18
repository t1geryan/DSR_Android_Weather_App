package com.example.weatherapp.domain.models

data class AppTheme(
    val themeKey: Int,
) {

    companion object {
        const val DAY_THEME_KEY = 0
        const val NIGHT_THEME_KEY = 1
        const val SYSTEM_THEME_KEY = 2
    }
}

data class AppUnitsSystem(
    val systemKey: Int,
) {

    companion object {
        const val METRIC_SYSTEM_KEY = 0
        const val IMPERIAL_SYSTEM_KEY = 1
    }
}