package com.example.weatherapp.presentation.ui_utils

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import com.example.weatherapp.utils.Constants
import java.time.Instant
import java.util.*

/**
 * Wrapper for transform unix utc timestamp to pattern regardless of the location of the device
 *
 * For example: unixUtcTimeToPattern(1684175138, "HH::mm") -> "18:25"
 * @param unixUtcTime unix timestamp in utc
 * @param pattern the form to which the [unixUtcTime] will be cast
 * @return [unixUtcTime] in [pattern] format
 */
fun unixUtcMillisTimeToPattern(
    unixUtcTime: Long,
    pattern: String,
    localeCode: String = Constants.Locale.DEFAULT_LOCALE_TAG
): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.forLanguageTag(localeCode)).apply {
        timeZone = TimeZone.getTimeZone(Constants.Time.UTC_TIME_ZONE_ID)
    }
    return simpleDateFormat.format(Date(unixUtcTime))
}

/**
 * Wrapper fro getting current time in location by it shift from utc time in seconds
 *
 * @param shiftFromUtcSeconds shift from utc time in seconds
 * @return current time for location in unix format
 */
fun getCurrentLocationTimeUnixMillis(shiftFromUtcSeconds: Long): Long {
    val now = Instant.now()
    return (now.epochSecond + shiftFromUtcSeconds) * Constants.Time.MILLIS_IN_SEC
}