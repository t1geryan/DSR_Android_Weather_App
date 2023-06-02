package com.example.weatherapp.presentation.ui_utils

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.weatherapp.presentation.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * viewModelScope with [Dispatchers.IO]
 * written to reduce the amount of code
 */
val ViewModel.viewModelScopeIO
    get() = CoroutineScope(SupervisorJob() + Dispatchers.IO)

/**
 * wrapper for getting data from the repository layer with errors and ui states handling
 * @param sharedFlow the [MutableSharedFlow] which will accept UiState with emit method
 * @param emitBlock the block of code which will emit [UiState.Success] value with [MutableSharedFlow.emit] on success
 */
fun <T> ViewModel.emitUiState(
    sharedFlow: MutableSharedFlow<UiState<T>>,
    emitBlock: suspend () -> Unit
) {
    viewModelScopeIO.launch {
        sharedFlow.emit(UiState.Loading())
        try {
            emitBlock()
        } catch (e: Exception) {
            sharedFlow.emit(UiState.Error(e))
        }
    }
}

/**
 * wrapper for getting data from the repository layer with errors and ui states handling
 * @param stateFlow the [MutableStateFlow] which will accept UiState to value property
 * @param collectBlock the block of code which will set value to [StateFlow.value] on success
 */
fun <T> ViewModel.collectUiState(
    stateFlow: MutableStateFlow<UiState<T>>,
    collectBlock: suspend () -> Unit
) {
    viewModelScopeIO.launch {
        stateFlow.value = UiState.Loading()
        try {
            collectBlock()
        } catch (e: Exception) {
            stateFlow.value = UiState.Error(e)
        }
    }
}

/**
 * wrapper for getting data from the repository layer with errors and ui states handling
 * @param stateFlow the [MutableStateFlow] which will accept UiState to value property
 * @param getDataFlowBlock the block which returns flow which will be collected
 * @see collectUiState
 */
fun <T> ViewModel.collectUiStateFromFlow(
    stateFlow: MutableStateFlow<UiState<T>>,
    getDataFlowBlock: suspend () -> Flow<T>,
) {
    collectUiState(stateFlow) {
        getDataFlowBlock().collect { data ->
            stateFlow.value = UiState.Success(data)
        }
    }
}

/**
 * wrapper for outputting errors when emit Flow or set StateFlow value
 * @param emitBlock block of code where emitting Flow or setting StateFlow value
 */
fun ViewModel.tryEmitFlow(
    emitBlock: suspend () -> Unit,
) {
    viewModelScopeIO.launch {
        try {
            emitBlock()
        } catch (e: Exception) {
            Log.w("Flow Emit Error", "Exception $e caught when emit flow")
            e.printStackTrace()
        }
    }
}