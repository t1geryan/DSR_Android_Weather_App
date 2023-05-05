package com.example.weatherapp.presenter.ui.base_locations_list_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemLocationBinding
import com.example.weatherapp.domain.models.LocationWeather

class LocationsAdapter : RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>() {

    var locationsWithWeather: List<LocationWeather> = emptyList()
        set(value) {
            val diffCallback = LocationsDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    class LocationsViewHolder(
        private val binding: ItemLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(locationWeather: LocationWeather) {
            val context = binding.root.context
            with(binding) {
                locationNameTV.text = locationWeather.location.name
                currentTempTV.text = context.getString(
                    R.string.current_temperature,
                    locationWeather.weatherForecast.first().temperature
                )
                tomorrowTempTV.visibility = View.GONE
                locationWeather.weatherForecast.getOrNull(1)?.let { tomorrowWeather ->
                    tomorrowTempTV.text = context.getString(
                        R.string.tomorrow_temperature,
                        tomorrowWeather.temperature
                    )
                    tomorrowTempTV.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationBinding.inflate(inflater, parent, false)

        return LocationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val locationWeather = locationsWithWeather[position]
        holder.bind(locationWeather)
    }

    override fun getItemCount(): Int = locationsWithWeather.size
}