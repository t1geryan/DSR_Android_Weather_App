package com.example.weatherapp.presentation.contract.toolbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * if a class (e.g. Fragment) implements this interface,
 * it means that it has a single custom action for the toolbar that needs to be displayed
 */
interface HasCustomActionToolbar {

    fun getCustomAction(): ToolbarAction
}

data class ToolbarAction(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val onAction: Runnable,
)