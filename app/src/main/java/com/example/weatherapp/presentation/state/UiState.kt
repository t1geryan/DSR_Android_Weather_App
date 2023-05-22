package com.example.weatherapp.presentation.state

sealed class UiState<T> {
    class Success<T>(val data: T) : UiState<T>()

    class Error<T>(val exception: Exception? = null) : UiState<T>()

    class Loading<T> : UiState<T>()
}