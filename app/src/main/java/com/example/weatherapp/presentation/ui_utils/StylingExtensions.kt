package com.example.weatherapp.presentation.ui_utils

import android.content.Context
import android.graphics.Color
import androidx.annotation.StringRes
import com.example.weatherapp.utils.Constants
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.color.MaterialColors

fun LineChart.styleTemperatureLineChart(context: Context, @StringRes titleRes: Int) {
    val colorSurface = MaterialColors.getColor(
        context, com.google.android.material.R.attr.colorSurface, Color.BLACK
    )
    val colorOnSurface = MaterialColors.getColor(
        context, com.google.android.material.R.attr.colorOnSurface, Color.BLACK
    )
    setNoDataText(context.getString(titleRes))
    setNoDataTextColor(colorOnSurface)
    setBackgroundColor(colorSurface)

    description.isEnabled = false
    setTouchEnabled(false)
    setScaleEnabled(false)
    isDragEnabled = false
    setDrawGridBackground(false)
    setDrawBorders(false)
    legend.isEnabled = false
    xAxis.isEnabled = false
    axisRight.isEnabled = false
    axisLeft.isEnabled = true
    axisLeft.axisLineColor = colorOnSurface
    axisLeft.textColor = colorOnSurface
}

fun LineDataSet.styleTemperatureLineDataSet(context: Context) {
    val colorPrimary = MaterialColors.getColor(
        context, com.google.android.material.R.attr.colorPrimary, Color.BLACK
    )
    val colorOnSurface = MaterialColors.getColor(
        context, com.google.android.material.R.attr.colorOnSurface, Color.BLACK
    )

    color = colorPrimary
    setCircleColor(colorPrimary)
    with(Constants.Theme.LineChart) {
        lineWidth = CHART_LINE_WIDTH
        circleRadius = CHART_CIRCLE_RADIUS
        circleHoleRadius = CHART_HOLE_RADIUS
        valueTextColor = colorOnSurface
    }
}