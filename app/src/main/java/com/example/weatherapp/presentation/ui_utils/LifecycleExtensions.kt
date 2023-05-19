package com.example.weatherapp.presentation.ui_utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Function for collecting Flow with [LifecycleOwner]
 *
 * Can be used only after [Fragment.onCreateView] and before [Fragment.onDestroyView] in Fragments
 * or after [Activity.onCreate] and before [Activity.onDestroy]
 */
fun <T> LifecycleOwner.collectFlow(flow: Flow<T>, onCollect: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                onCollect(it)
            }
        }
    }
}