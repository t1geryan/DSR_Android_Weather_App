package com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_locations_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemCheckLocationBinding
import com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_locations_screen.adapter.locationcheckitem.LocationCheckItem

class LocationsCheckAdapter
    : RecyclerView.Adapter<LocationsCheckAdapter.LocationsCheckViewHolder>() {

    private var _locationCheckItems: MutableList<LocationCheckItem> = mutableListOf()
    var locationCheckItems: List<LocationCheckItem> = emptyList()
        set(value) {
            val diffCallback = LocationsCheckDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
            _locationCheckItems = value.toMutableList()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsCheckViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCheckLocationBinding.inflate(inflater, parent, false)
        return LocationsCheckViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationsCheckViewHolder, position: Int) {
        val locationCheckItem = locationCheckItems[position]
        holder.bind(locationCheckItem, position)
    }

    override fun getItemCount(): Int = locationCheckItems.size

    fun getSelectedLocationsId(): List<Long> =
        _locationCheckItems
            .filter {
                it.isSelected
            }
            .map {
                it.location.id
            }

    inner class LocationsCheckViewHolder(
        private val binding: ItemCheckLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(locationCheckItem: LocationCheckItem, position: Int) {
            with(binding) {
                locationCB.apply {
                    text = locationCheckItem.location.name

                    isSelected = locationCheckItem.isSelected
                    setOnCheckedChangeListener { _, isSelected ->
                        val item = _locationCheckItems[position]
                        _locationCheckItems[position] = item.copy(isSelected = isSelected)
                    }
                }
            }
        }
    }
}