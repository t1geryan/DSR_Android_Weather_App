package com.example.weatherapp.data.remote.weather.api

import com.example.weatherapp.data.remote.weather.dto.TriggerRequestDto
import com.example.weatherapp.data.remote.weather.dto.TriggerResponseDto
import retrofit2.Response
import retrofit2.http.*

interface TriggersApi {

    @POST("data/3.0/triggers")
    suspend fun createTrigger(
        @Body triggerRequestDto: TriggerRequestDto,
        @Query("appid") apiKey: String,
    ): Response<TriggerResponseDto>

    @GET("data/3.0/triggers/{id}")
    suspend fun getTriggerById(
        @Path("id") id: String,
        @Query("appid") apiKey: String,
    ): Response<TriggerResponseDto>

    @PUT("data/3.0/triggers/{id}")
    suspend fun changeTriggerById(
        @Body triggerRequestDto: TriggerRequestDto,
        @Path("id") id: String,
        @Query("appid") apiKey: String,
    ): Response<TriggerResponseDto>

    @DELETE("data/3.0/triggers/{id}")
    suspend fun deleteTriggerById(
        @Path("id") id: String,
        @Query("appid") apiKey: String,
    ): Response<Unit>

}