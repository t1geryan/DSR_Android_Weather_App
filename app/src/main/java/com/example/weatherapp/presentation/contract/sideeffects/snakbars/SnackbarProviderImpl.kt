package com.example.weatherapp.presentation.contract.sideeffects.snakbars

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class SnackbarProviderImpl @Inject constructor(
    @ActivityContext private val context: Context,
) : SnackbarProvider {

    override fun showSnackBar(
        view: View,
        @StringRes stringRes: Int,
        duration: Int,
        @StringRes actionTitle: Int?,
        onAction: View.OnClickListener?
    ) {
        showSnackBar(
            view,
            context.getString(stringRes),
            duration,
            actionTitle?.let { context.getString(it) },
            onAction
        )
    }

    override fun showSnackBar(
        view: View,
        message: String,
        duration: Int,
        actionTitle: String?,
        onAction: View.OnClickListener?
    ) {
        val snackbar = Snackbar.make(view, message, duration)
        if (actionTitle != null && onAction != null)
            snackbar.setAction(actionTitle, onAction)
        snackbar.show()
    }
}