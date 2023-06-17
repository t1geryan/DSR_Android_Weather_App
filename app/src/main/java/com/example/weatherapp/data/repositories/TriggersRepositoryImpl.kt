package com.example.weatherapp.data.repositories

import com.example.weatherapp.domain.models.Trigger
import com.example.weatherapp.domain.repositories.TriggersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TriggersRepositoryImpl @Inject constructor(

) : TriggersRepository {

    override fun getTriggersList(): Flow<List<Trigger>> {
        TODO("Not yet implemented")
    }

    override fun getTriggerById(id: Long): Flow<Trigger> {
        TODO("Not yet implemented")
    }

    override suspend fun addTrigger(trigger: Trigger) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTrigger(trigger: Trigger) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTriggerById(id: Long) {
        TODO("Not yet implemented")
    }
}