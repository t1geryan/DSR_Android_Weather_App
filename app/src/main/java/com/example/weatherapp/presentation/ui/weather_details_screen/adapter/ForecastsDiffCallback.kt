package com.example.weatherapp.presentation.ui.weather_details_screen.adapter

import androidx.recyclerview.widget.DiffUtil

class ForecastsDiffCallback(
    private val oldList: List<ForecastItem>,
    private val newList: List<ForecastItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldForecastItem = oldList[oldItemPosition]
        val newForecastItem = newList[newItemPosition]

        return oldForecastItem.dateTimeUnixUtc == newForecastItem.dateTimeUnixUtc
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldForecastItem = oldList[oldItemPosition]
        val newForecastItem = newList[newItemPosition]

        return oldForecastItem == newForecastItem
    }
}