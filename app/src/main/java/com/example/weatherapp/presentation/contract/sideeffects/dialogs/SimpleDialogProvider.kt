package com.example.weatherapp.presentation.contract.sideeffects.dialogs

import android.content.DialogInterface
import androidx.annotation.StringRes


interface SimpleDialogProvider {

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