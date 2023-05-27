package com.example.weatherapp.presentation.ui_utils

import android.os.Build
import android.os.Bundle

/**
 * Wrapper for getting parcelable from bundle
 */
@Suppress("DEPRECATION")
fun <T> Bundle.getParcelableData(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, clazz)
    } else {
        getParcelable(key)
    }
}