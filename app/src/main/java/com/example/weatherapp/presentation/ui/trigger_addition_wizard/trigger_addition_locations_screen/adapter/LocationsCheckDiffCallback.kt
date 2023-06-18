package com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_locations_screen.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_locations_screen.adapter.locationcheckitem.LocationCheckItem

class LocationsCheckDiffCallback(
    private val oldList: List<LocationCheckItem>,
    private val newList: List<LocationCheckItem>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldLocationCheckItem = oldList[oldItemPosition]
        val newLocationCheckItem = newList[newItemPosition]

        return oldLocationCheckItem.location.id == newLocationCheckItem.location.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldLocationCheckItem = oldList[oldItemPosition]
        val newLocationCheckItem = newList[newItemPosition]

        return newLocationCheckItem == oldLocationCheckItem
    }
}