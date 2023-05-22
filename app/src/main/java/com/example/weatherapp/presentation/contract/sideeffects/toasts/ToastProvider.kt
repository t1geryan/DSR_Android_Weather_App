package com.example.weatherapp.presentation.contract.sideeffects.toasts

import androidx.annotation.StringRes

interface ToastProvider {

    /**
     * Shows Toast from string resource
     * @param stringRes string resource to be shown
     */
    fun showToast(@StringRes stringRes: Int)

    /**
     * Shows Toast with message
     * @param message to be shown
    text to be shown
     */
    fun showToast(message: String)
}