package com.example.weatherapp.domain.repositories

import com.example.weatherapp.domain.models.Trigger
import kotlinx.coroutines.flow.Flow

interface TriggersRepository {

    fun getTriggersList(): Flow<List<Trigger>>

    fun getTriggerById(id: Long): Flow<Trigger>

    suspend fun addTrigger(trigger: Trigger)

    suspend fun updateTrigger(trigger: Trigger)

    suspend fun deleteTriggerById(id: Long)
}