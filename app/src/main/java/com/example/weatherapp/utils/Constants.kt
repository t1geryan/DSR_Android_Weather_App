package com.example.weatherapp.utils

object Constants {
    object Time {
        const val MILLIS_IN_SEC = 1000
        const val MIDDAY_TIME = 12
        const val UTC_TIME_ZONE_ID = "UTC"

        const val HOUR_MINUTE_PATTERN = "HH:mm"
        const val WEEKDAY_HOUR_MINUTE_PATTERN = "EEE, HH:mm"
    }

    object WindDirections {
        const val DIRECTIONS_INTERVAL_DEGREES = 45
        const val MAX_DIRECTION_DEGREES = 360
    }

    object Weather {
        const val FORECASTS_COUNT_FOR_3_DAYS = 24U
    }

    object PrefDataStore {
        const val NAME = "APP_PREFERENCES_DATA_STORE"
    }

    const val MAP_API_KEY = "a122b20c-3546-43ca-8092-560ed12702f6"
    const val OPEN_WEATHER_API_KEY = "0fd028a9364ad11692c9397667b1414f"
}