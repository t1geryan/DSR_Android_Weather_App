package com.example.weatherapp.presentation.contract.sideeffects.dialogs

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.example.weatherapp.presentation.ui_utils.getStringOrNull
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class SimpleDialogProviderImpl @Inject constructor(
    @ActivityContext private val context: Context
) : SimpleDialogProvider {

    override fun showSimpleDialog(
        title: String,
        message: String,
        isCancelable: Boolean,
        positiveButtonTitle: String?,
        negativeButtonTitle: String?,
        neutralButtonTitle: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(isCancelable)
            .apply {
                positiveButtonTitle?.let {
                    setPositiveButton(it, onClickListener)
                }
                negativeButtonTitle?.let {
                    setNegativeButton(it, onClickListener)
                }
                neutralButtonTitle?.let {
                    setNeutralButton(it, onClickListener)
                }
            }
            .show()
    }

    override fun showSimpleDialog(
        titleRes: Int,
        messageRes: Int,
        isCancelable: Boolean,
        positiveButtonTitleRes: Int?,
        negativeButtonTitleRes: Int?,
        neutralButtonTitleRes: Int?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        with(context) {
            showSimpleDialog(
                getString(titleRes),
                getString(messageRes),
                isCancelable,
                getStringOrNull(positiveButtonTitleRes),
                getStringOrNull(negativeButtonTitleRes),
                getStringOrNull(neutralButtonTitleRes),
                onClickListener
            )
        }
    }
}