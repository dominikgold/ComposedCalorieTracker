package com.dominikgold.calorietracker.ui.common.linechart

import com.dominikgold.calorietracker.entities.Percentage

data class Point(val x: Percentage, val y: Percentage)

internal fun createPoints(xAxisValues: List<Percentage>, yAxisValues: List<Percentage>): List<Point> {
    return if (xAxisValues.size >= yAxisValues.size) {
        xAxisValues.mapIndexed { index, value -> Point(value, yAxisValues[index]) }
    } else {
        yAxisValues.mapIndexed { index, value -> Point(xAxisValues[index], value) }
    }
}

internal fun createPoints(yAxisValues: List<Percentage>): List<Point> {
    return yAxisValues.mapIndexed { index, value ->
        Point(x = index.toDouble() / (yAxisValues.size - 1).toDouble(), y = value)
    }
}
