package com.example.weatherapp.presentation.contract.sideeffects.toasts

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ToastProviderImpl @Inject constructor(
    @ActivityContext private val context: Context
) : ToastProvider {

    override fun showToast(stringRes: Int) {
        showToast(context.getString(stringRes))
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}