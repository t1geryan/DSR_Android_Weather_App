package com.example.weatherapp.di

import com.example.weatherapp.presentation.contract.sideeffects.dialogs.SimpleDialogProvider
import com.example.weatherapp.presentation.contract.sideeffects.dialogs.SimpleDialogProviderImpl
import com.example.weatherapp.presentation.contract.sideeffects.snakbars.SnackbarProvider
import com.example.weatherapp.presentation.contract.sideeffects.snakbars.SnackbarProviderImpl
import com.example.weatherapp.presentation.contract.sideeffects.toasts.ToastProvider
import com.example.weatherapp.presentation.contract.sideeffects.toasts.ToastProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class SideEffectsModule {

    @Binds
    @ActivityScoped
    abstract fun bindsToastProvider(
        toastProviderImpl: ToastProviderImpl
    ): ToastProvider

    @Binds
    @ActivityScoped
    abstract fun bindsSnackbarProvider(
        snackbarProviderImpl: SnackbarProviderImpl
    ): SnackbarProvider

    @Binds
    @ActivityScoped
    abstract fun bindsDialogProvider(
        simpleDialogProviderImpl: SimpleDialogProviderImpl
    ): SimpleDialogProvider
}