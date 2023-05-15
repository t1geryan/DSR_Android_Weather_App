package com.example.weatherapp.presentation.ui_utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.core.content.ContextCompat
import com.example.weatherapp.R
import com.example.weatherapp.utils.Constants
import java.util.*
import kotlin.math.absoluteValue

fun Context.getBitmapFromVectorDrawable(drawableId: Int): Bitmap? {
    val drawable = ContextCompat.getDrawable(this, drawableId) ?: return null

    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    ) ?: return null
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}

/**
 * Wrapper for transform unix utc timestamp to pattern regardless of the location of the device
 *
 * For example: unixUtcTimeToPattern(1684175138, "HH::mm") -> "18:25"
 * @param unixUtcTime unix timestamp in utc
 * @param pattern the form to which the [unixUtcTime] will be cast
 * @return [unixUtcTime] in [pattern] format
 */
fun Context.unixUtcTimeToPattern(unixUtcTime: Long, pattern: String): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.ROOT).apply {
        timeZone = TimeZone.getTimeZone(Constants.Time.UTC_TIME_ZONE_ID)
    }
    return simpleDateFormat.format(Date(unixUtcTime))
}

// todo: add different metric systems support
/**
 * Wrapper for simple getting R.string.temperature from string.xml
 *
 * For example: 14->+14°C; -27 -> -27°F
 * @param value temperature value
 * @return string format for temperature
 * @see Context.getString
 */
fun Context.getTemperatureString(value: Int): String =
    getString(
        R.string.temperature,
        if (value < 0) "-" else "+",
        value.absoluteValue,
        getString(R.string.metric_temp_unit)
    )