package com.dominikgold.calorietracker.ui.common.linechart

import com.dominikgold.calorietracker.entities.Percentage

fun interpolateBetweenYAxisData(
    originalYAxisData: List<Percentage>,
    targetYAxisData: List<Percentage>,
    progress: Float,
): List<Point> {
    require(progress in 0f..1f) { "progress value should be between 0 and 1" }
    val originalXAxisValues = (originalYAxisData.indices).map {
        it.toDouble() / (originalYAxisData.size - 1).toDouble()
    }
    val targetXAxisValues = (targetYAxisData.indices).map {
        it.toDouble() / (targetYAxisData.size - 1).toDouble()
    }
    val originalDataPoints = createPoints(originalXAxisValues, originalYAxisData)
    val targetDataPoints = createPoints(targetXAxisValues, targetYAxisData)
    if (progress >= 1f) {
        return targetDataPoints
    }
    if (progress <= 0f) {
        return originalDataPoints
    }
    if (originalYAxisData.size == targetYAxisData.size) {
        return originalDataPoints.mapIndexed { index, point ->
            Point(point.x, interpolateYAxisValue(point.y, targetYAxisData[index], progress))
        }
    }
    val dataPointsAtOriginalXAxisValues = originalDataPoints.findPointsAtXAxisValuesOnOther(
        otherDataPoints = targetDataPoints,
        progress = progress,
        reverseProgressCalculation = true,
    )
    val dataPointsAtTargetXAxisValues = targetDataPoints.filter { pointToFilter ->
        dataPointsAtOriginalXAxisValues.none {
            pointToFilter.x == it.x
        }
    }.findPointsAtXAxisValuesOnOther(
        otherDataPoints = originalDataPoints,
        progress = progress,
        reverseProgressCalculation = false,
    )
    return (dataPointsAtOriginalXAxisValues + dataPointsAtTargetXAxisValues).sortedBy { it.x }
}

private fun List<Point>.findPointsAtXAxisValuesOnOther(
    otherDataPoints: List<Point>,
    progress: Float,
    reverseProgressCalculation: Boolean,
): List<Point> {
    val otherDataPointsIterator = otherDataPoints.listIterator()
    var currentOtherPoint = otherDataPointsIterator.next()
    var previousOtherPoint = currentOtherPoint
    return this.map { sourcePoint ->
        while (sourcePoint.x > currentOtherPoint.x) {
            previousOtherPoint = currentOtherPoint
            currentOtherPoint = otherDataPointsIterator.next()
        }
        val deltaToSourceXValue = sourcePoint.x - previousOtherPoint.x
        val deltaToOtherXValue = currentOtherPoint.x - previousOtherPoint.x
        val normalizedDeltaToSourceXValue =
            if (deltaToOtherXValue > 0.0) deltaToSourceXValue / deltaToOtherXValue else 0.0
        val deltaToOtherYValue = currentOtherPoint.y - previousOtherPoint.y
        val otherYValueAtSourceXValue = previousOtherPoint.y + deltaToOtherYValue * normalizedDeltaToSourceXValue
        return@map Point(
            x = sourcePoint.x,
            y = if (reverseProgressCalculation) {
                sourcePoint.y + (otherYValueAtSourceXValue - sourcePoint.y) * progress
            } else {
                otherYValueAtSourceXValue + (sourcePoint.y - otherYValueAtSourceXValue) * progress
            },
        )
    }
}

private fun interpolateYAxisValue(originalValue: Percentage, targetValue: Percentage, progress: Float): Percentage {
    val delta = targetValue - originalValue
    return originalValue + delta * progress
}

private fun createPoints(xAxisValues: List<Percentage>, yAxisValues: List<Percentage>): List<Point> {
    return if (xAxisValues.size >= yAxisValues.size) {
        xAxisValues.mapIndexed { index, value -> Point(value, yAxisValues[index]) }
    } else {
        yAxisValues.mapIndexed { index, value -> Point(xAxisValues[index], value) }
    }
}

data class Point(val x: Percentage, val y: Percentage)