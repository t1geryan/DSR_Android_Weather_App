package com.example.weatherapp.presentation.contract.sideeffects.snakbars

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

interface SnackbarProvider {

    /**
     * Shows SnackBar with message and action button optionally
     * @param stringRes string resource to be shown
     * @duration show duration [Snackbar.LENGTH_SHORT], [Snackbar.LENGTH_LONG], [Snackbar.LENGTH_INDEFINITE]
     * @param actionTitle action title string resource (optionally)
     * @onAction lambda that will be launched when the snackbar's button is clicked (optionally)
     */
    fun showSnackBar(
        view: View,
        @StringRes stringRes: Int,
        duration: Int,
        @StringRes actionTitle: Int?,
        onAction: View.OnClickListener?
    )

    /**
     * Shows SnackBar with message and action button optionally
     * @param message message to be shown
     * @duration show duration [Snackbar.LENGTH_SHORT], [Snackbar.LENGTH_LONG], [Snackbar.LENGTH_INDEFINITE]
     * @param actionTitle action title (optionally)
     * @onAction lambda that will be launched when the snackbar's button is clicked (optionally)
     */
    fun showSnackBar(
        view: View,
        message: String,
        duration: Int,
        actionTitle: String?,
        onAction: View.OnClickListener?
    )
}