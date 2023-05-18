package com.example.weatherapp.presentation.ui.base_locations_list_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemLocationBinding
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.presentation.ui_utils.getTemperatureString
import com.example.weatherapp.utils.Constants
import java.util.*

class LocationsAdapter(
    private val unitsSystemSetting: AppUnitsSystem,
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

    inner class LocationsViewHolder(
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

                // show current temperature
                currentTempTV.text = context.getString(
                    R.string.current_temperature,
                    context.getTemperatureString(
                        locationItem.currentWeather.temperature,
                        unitsSystemSetting.systemKey
                    )
                )
                currentTempTV.visibility = View.VISIBLE

                // show tomorrow's temperature if needed and if it exists
                tomorrowTempTV.visibility = View.GONE
                if (locationItem.location.hasNextDayForecast) {
                    val tomorrowMidDayUnixUtcTimestamp = getNextDayMidDayUnixUtcTimestamp()
                    // find tomorrow midday temperature in forecasts and show it as tomorrow temp
                    locationItem.weatherForecasts.firstOrNull { it.dateTimeUnixUtc >= tomorrowMidDayUnixUtcTimestamp }
                        ?.let { tomorrowWeather ->
                            tomorrowTempTV.text = context.getString(
                                R.string.tomorrow_temperature,
                                context.getTemperatureString(
                                    tomorrowWeather.temperature,
                                    unitsSystemSetting.systemKey
                                )
                            )
                            tomorrowTempTV.visibility = View.VISIBLE
                        }
                }

                favoriteStatusIV.setImageResource(
                    if (locationItem.location.isFavorite)
                        R.drawable.icon_favorite_24
                    else
                        R.drawable.icon_favorite_border_24
                )
            }
        }

        private fun getNextDayMidDayUnixUtcTimestamp(): Long {
            val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.add(Calendar.DAY_OF_MONTH, 1) // add one day to current day
            calendar.set(Calendar.HOUR_OF_DAY, Constants.Time.MIDDAY_TIME)
            calendar.set(Calendar.MINUTE, 0) // make no minutes
            calendar.set(Calendar.SECOND, 0) // make no seconds
            return calendar.timeInMillis / Constants.Time.MILLIS_IN_SEC
        }
    }
}