package com.example.weatherapp.presentation.contract

import android.content.DialogInterface
import android.view.View.OnClickListener
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

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
     * @param message to be shown
    text to be shown
     */
    fun showToast(message: String)

    /**
     * Shows SnackBar with message and action button optionally
     * @param stringRes string resource to be shown
     * @duration show duration [Snackbar.LENGTH_SHORT], [Snackbar.LENGTH_LONG], [Snackbar.LENGTH_INDEFINITE]
     * @param actionTitle action title string resource (optionally)
     * @onAction lambda that will be launched when the snackbar's button is clicked (optionally)
     */
    fun showSnackBar(
        @StringRes stringRes: Int,
        duration: Int,
        @StringRes actionTitle: Int?,
        onAction: OnClickListener?
    )

    /**
     * Shows SnackBar with message and action button optionally
     * @param message message to be shown
     * @duration show duration [Snackbar.LENGTH_SHORT], [Snackbar.LENGTH_LONG], [Snackbar.LENGTH_INDEFINITE]
     * @param actionTitle action title (optionally)
     * @onAction lambda that will be launched when the snackbar's button is clicked (optionally)
     */
    fun showSnackBar(
        message: String,
        duration: Int,
        actionTitle: String?,
        onAction: OnClickListener?
    )

    /**
     * Shows simple dialog with title, message, buttons (each button optionally) using string
     * @param title dialog's title string
     * @param message dialog's message string
     * @param isCancelable dialog's cancelable property
     * @param positiveButtonTitle dialog's positive button title (if null positive button will not be shown)
     * @param negativeButtonTitle dialog's negative button title (if null negative button will not be shown)
     * @param neutralButtonTitle dialog's neutral button title (if null neutral button will not be shown)
     * @param onClickListener is [DialogInterface.OnClickListener] or null
     */
    fun showSimpleDialog(
        title: String,
        message: String,
        isCancelable: Boolean,
        positiveButtonTitle: String?,
        negativeButtonTitle: String?,
        neutralButtonTitle: String?,
        onClickListener: DialogInterface.OnClickListener?
    )

    /**
     * Shows simple dialog with title, message, buttons (each button optionally) using string resources
     * @param titleRes dialog's title string resource
     * @param messageRes dialog's message string resource
     * @param isCancelable dialog's cancelable property
     * @param positiveButtonTitleRes dialog's positive button title (if null positive button will not be shown)
     * @param negativeButtonTitleRes dialog's negative button title (if null negative button will not be shown)
     * @param neutralButtonTitleRes dialog's neutral button title (if null neutral button will not be shown)
     * @param onClickListener is [DialogInterface.OnClickListener] or null
     */
    fun showSimpleDialog(
        @StringRes titleRes: Int,
        @StringRes messageRes: Int,
        isCancelable: Boolean,
        @StringRes positiveButtonTitleRes: Int?,
        @StringRes negativeButtonTitleRes: Int?,
        @StringRes neutralButtonTitleRes: Int?,
        onClickListener: DialogInterface.OnClickListener?
    )
}