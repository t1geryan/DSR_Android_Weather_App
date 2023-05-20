package com.example.weatherapp.presentation.ui.weather_details_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemForecastBinding
import com.example.weatherapp.presentation.contract.UnitsSystemApi
import com.example.weatherapp.presentation.ui_utils.getTemperatureString
import com.example.weatherapp.presentation.ui_utils.unixUtcTimeToPattern
import com.example.weatherapp.utils.Constants

class ForecastsAdapter(
    private val unitsSystemApi: UnitsSystemApi
) : RecyclerView.Adapter<ForecastsAdapter.ForecastsViewHolder>() {

    var forecastsList: List<ForecastItem> = emptyList()
        set(value) {
            val diffCallback = ForecastsDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemForecastBinding.inflate(inflater, parent, false)

        return ForecastsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastsViewHolder, position: Int) {
        val forecastItem = forecastsList[position]
        holder.bind(forecastItem)
    }

    override fun getItemCount(): Int = forecastsList.size

    inner class ForecastsViewHolder(
        val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(forecastItem: ForecastItem) {
            val context = binding.root.context
            with(binding) {
                val temp = forecastItem.temperature
                forecastTemperatureTV.text =
                    context.getTemperatureString(
                        temp,
                        unitsSystemApi.getCurrentUnitsSystem()
                    )

                Glide.with(context)
                    .load(
                        context.getString(
                            R.string.weather_icon_path,
                            forecastItem.weatherIconName
                        )
                    )
                    .error(R.drawable.icon_weather_cloudy_24)
                    .placeholder(R.drawable.icon_weather_cloudy_24)
                    .into(forecastIcon)

                val forecastedTimeUnixMillis =
                    (forecastItem.dateTimeUnixUtc + forecastItem.shiftFromUtcSeconds) * Constants.Time.MILLIS_IN_SEC
                forecastTimeTV.text = context.unixUtcTimeToPattern(
                    forecastedTimeUnixMillis,
                    Constants.Time.HOUR_MINUTE_PATTERN
                )
            }
        }
    }
}