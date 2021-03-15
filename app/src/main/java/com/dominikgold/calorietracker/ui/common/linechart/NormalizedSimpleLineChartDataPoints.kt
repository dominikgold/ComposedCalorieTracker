package com.dominikgold.calorietracker.ui.common.linechart

import com.dominikgold.calorietracker.entities.Percentage

data class NormalizedSimpleLineChartDataPoints(
    private val original: List<SimpleLineChartDataPoint>,
    private val padding: Double,
) {

    val normalizedYAxisValues: List<Percentage> = original.let { originalDataPoints ->
        if (originalDataPoints.isEmpty()) {
            return@let listOf()
        }

        val maxYAxisValue = originalDataPoints.maxOf { it.yAxisValue }
        val minYAxisValue = originalDataPoints.minOf { it.yAxisValue }
        val yAxisRange = maxYAxisValue - minYAxisValue
        val maxYAxisValueWithPadding = maxYAxisValue + yAxisRange * padding
        val minYAxisValueWithPadding = minYAxisValue - yAxisRange * padding
        val yAxisRangeWithPadding = maxYAxisValueWithPadding - minYAxisValueWithPadding
        return@let originalDataPoints.map {
            (it.yAxisValue - minYAxisValueWithPadding) / yAxisRangeWithPadding
        }
    }

    val xAxisDescriptions: List<String?> = original.map { it.description }

}