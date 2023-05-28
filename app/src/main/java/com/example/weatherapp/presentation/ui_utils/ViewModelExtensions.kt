package com.example.weatherapp.presentation.ui_utils

import androidx.lifecycle.ViewModel
import com.example.weatherapp.presentation.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * viewModelScope with [Dispatchers.IO]
 * written to reduce the amount of code
 */
val ViewModel.viewModelScopeIO
    get() = CoroutineScope(SupervisorJob() + Dispatchers.IO)

/**
 * wrapper for getting data from the repository layer with errors and ui states handling
 * @param dataFlow is the data from the repository layer that needs to be handled
 * @param stateFlow is the [MutableStateFlow] which will accept UiState to value property
 */
fun <T> ViewModel.collectUiState(
    dataFlow: Flow<T>,
    stateFlow: MutableStateFlow<UiState<T>>,
) = viewModelScopeIO.launch {
    stateFlow.value = UiState.Loading()
    try {
        dataFlow.collect { data ->
            stateFlow.value = UiState.Success(data)
        }
    } catch (e: Exception) {
        stateFlow.value = UiState.Error(e)
    }
}