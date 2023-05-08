package com.example.weatherapp.presentation.ui.base_locations_list_screen.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherapp.domain.models.LocationWeather

class LocationsDiffCallback(
    private val oldList: List<LocationWeather>,
    private val newList: List<LocationWeather>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldLocation = oldList[oldItemPosition]
        val newLocation = newList[newItemPosition]

        return oldLocation.location.id == newLocation.location.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldLocation = oldList[oldItemPosition]
        val newLocation = newList[newItemPosition]

        return oldLocation == newLocation
    }
}