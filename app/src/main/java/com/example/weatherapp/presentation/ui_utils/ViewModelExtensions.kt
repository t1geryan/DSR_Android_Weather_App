package com.example.weatherapp.presentation.ui_utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.presentation.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

val ViewModel.viewModelScope: CoroutineScope
    get() = CoroutineScope(SupervisorJob() + Dispatchers.IO)

fun <T> ViewModel.collectUiState(
    dataFlow: Flow<T>,
    stateFlow: MutableStateFlow<UiState<T>>,
) = viewModelScope.launch {
    stateFlow.value = UiState.Loading()
    try {
        dataFlow.collect { data ->
            stateFlow.value = UiState.Success(data)
        }
    } catch (e: Exception) {
        stateFlow.value = UiState.Error(e.message)
    }
}