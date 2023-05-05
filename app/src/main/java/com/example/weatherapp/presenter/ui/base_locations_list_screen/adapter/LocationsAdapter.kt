package com.example.weatherapp.presenter.ui.base_locations_list_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemLocationBinding
import com.example.weatherapp.domain.models.LocationItem
import com.example.weatherapp.presenter.contract.LocationItemClickListener

class LocationsAdapter(
    private val listener: LocationItemClickListener
) : RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>(), View.OnClickListener {

    var locationsWithWeather: List<LocationItem> = emptyList()
        set(value) {
            val diffCallback = LocationsDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.favoriteStatusIV.setOnClickListener(this)

        return LocationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val itemLocation = locationsWithWeather[position]
        holder.bind(itemLocation)
    }

    override fun getItemCount(): Int = locationsWithWeather.size

    /**
     * Add LocationItem to view.tag if want to get it in onClick
     * Requires adding view.setOnClickListener(this) for view while creating View Holder
     * @see onCreateViewHolder
     * @see LocationsViewHolder.setTags
     */
    override fun onClick(view: View) {
        val locationItem = view.tag as LocationItem
        when (view.id) {
            R.id.favoriteStatusIV -> listener.changeFavoriteStatus(locationItem)
            else -> listener.showDetails(locationItem)
        }
    }

    class LocationsViewHolder(
        private val binding: ItemLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * This function sets LocationItem to binding views tags so they can be taken in OnClick
         * @see LocationItem
         */
        private fun setTags(data: Any) {
            with(binding) {
                root.tag = data
                favoriteStatusIV.tag = data
            }
        }

        fun bind(locationItem: LocationItem) {
            setTags(locationItem)
            val context = binding.root.context
            with(binding) {
                locationNameTV.text = locationItem.location.name

                currentTempTV.visibility = View.GONE
                locationItem.weatherForecast.getOrNull(0)?.let { currentWeather ->
                    currentTempTV.text = context.getString(
                        R.string.current_temperature,
                        currentWeather.temperature.toString()
                    )
                    currentTempTV.visibility = View.VISIBLE
                }
                tomorrowTempTV.visibility = View.GONE
                locationItem.weatherForecast.getOrNull(1)?.let { tomorrowWeather ->
                    tomorrowTempTV.text = context.getString(
                        R.string.tomorrow_temperature,
                        tomorrowWeather.temperature.toString()
                    )
                    tomorrowTempTV.visibility = View.VISIBLE
                }

                favoriteStatusIV.setImageResource(
                    if (locationItem.location.isFavorite)
                        R.drawable.icon_favorite_24
                    else
                        R.drawable.icon_favorite_border_24
                )
            }
        }
    }
}