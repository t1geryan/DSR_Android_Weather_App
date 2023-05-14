package com.example.weatherapp.presentation.ui.weather_details_screen.adapter

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemForecastBinding
import java.util.*

class ForecastsAdapter : RecyclerView.Adapter<ForecastsAdapter.ForecastsViewHolder>() {

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

    class ForecastsViewHolder(
        val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(forecastItem: ForecastItem) {
            val context = binding.root.context
            with(binding) {
                val temp = forecastItem.temperature
                val sign = getSignOfTemperature(temp)
                forecastTemperatureTV.text = context.getString(R.string.temperature, sign, temp)

                Glide.with(context)
                    .load("https://openweathermap.org/img/wn/${forecastItem.weatherIconName}@2x.png")
                    .error(R.drawable.icon_weather_cloudy_24)
                    .placeholder(R.drawable.icon_weather_cloudy_24)
                    .into(forecastIcon)

                // todo: fix bug: Three extra hours are added to the time of measurement
                // bad fix: sub 10800 seconds from forecastedTimeUnix
                val forecastedTimeUnix =
                    (forecastItem.dateTimeUnixUtc + forecastItem.shiftFromUtcSeconds - 10800) * 1000
                val forecastedTimeText =
                    SimpleDateFormat("HH:mm", Locale.ENGLISH).format(Date(forecastedTimeUnix))
                forecastTimeTV.text = forecastedTimeText
            }
        }

        private fun getSignOfTemperature(temp: Int): String = if (temp < 0) {
            "-"
        } else if (temp == 0) {
            ""
        } else {
            "+"
        }
    }
}