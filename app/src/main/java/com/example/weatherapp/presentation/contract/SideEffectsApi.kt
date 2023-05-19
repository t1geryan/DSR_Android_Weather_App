package com.example.weatherapp.presentation.contract

import androidx.annotation.StringRes

/**
 * API allowing easy access to side effects (toasts, dialog etc.) from any screen
 */
interface SideEffectsApi {

    /**
     * Shows Toast from string resource
     * @param stringRes string resource to be shown
     */
    fun showToast(@StringRes stringRes: Int)

    /**
     * Shows Toast with message
     * @param message
    text to be shown
     */
    fun showToast(message: String)
}