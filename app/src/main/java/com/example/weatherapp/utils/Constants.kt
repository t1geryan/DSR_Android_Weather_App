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

    object WeatherApi {
        const val METRIC_UNITS_SYSTEM_API_VALUE = "metric"
        const val IMPERIAl_UNITS_SYSTEM_API_VALUE = "imperial"
    }

    object PrefDataStore {
        const val NAME = "APP_PREFERENCES_DATA_STORE"
    }

    object DELAY {
        const val SWIPE_TO_REFRESH_END_DELAY = 300L
    }

    object Locale {
        private const val ENGLISH_LOCALE_TAG = "en"
        private const val RUSSIAN_LOCALE_TAG = "ru"
        private const val US_RFS_CODE = "US"
        private const val RU_RFS_CODE = "RU"
        const val DEFAULT_LOCALE_TAG = ENGLISH_LOCALE_TAG

        /**
         * Holds all supported locale and regions codes in pairs
         *
         * For example: ru->RU, en->US
         */
        val SupportedLocaleAndRegionCodes =
            mapOf(ENGLISH_LOCALE_TAG to US_RFS_CODE, RUSSIAN_LOCALE_TAG to RU_RFS_CODE)
    }

    object Theme {
        object LineChart {
            const val CHART_LINE_WIDTH = 1.75f
            const val CHART_CIRCLE_RADIUS = 3.0f
            const val CHART_HOLE_RADIUS = 0.5f
        }
    }

    const val MAP_API_KEY = "a122b20c-3546-43ca-8092-560ed12702f6"
    const val OPEN_WEATHER_API_KEY = "0fd028a9364ad11692c9397667b1414f"
}