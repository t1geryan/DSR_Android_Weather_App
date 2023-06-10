package com.example.weatherapp.di.ui

import com.example.weatherapp.presentation.ui.trigger_addition_screen.adapters.condition.conditionitem.ConditionDomainPresentationMapper
import com.example.weatherapp.presentation.ui.trigger_addition_screen.adapters.condition.conditionitem.ConditionDomainPresentationMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MappersModule {

    @Binds
    @Singleton
    abstract fun bindsConditionMapper(
        conditionDomainPresentationMapperImpl: ConditionDomainPresentationMapperImpl
    ): ConditionDomainPresentationMapper
}