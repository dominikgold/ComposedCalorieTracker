package com.dominikgold.calorietracker.util

import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

fun Double.format(digitsAfterDecimal: Int = 1): String {
    var formatPattern = "0."
    repeat(digitsAfterDecimal) {
        formatPattern += "#"
    }
    return DecimalFormat(formatPattern).format(this)
}

/**
 * Parse a String to a Float respecting both commas and dots used as decimal points.
 */
fun String.parseLocalizedDouble(): Double? {
    return this.replace(',', '.').toDoubleOrNull()
}

fun List<Double>.rangeRoundedToNearestFive(): ClosedRange<Double> {
    val max = this.maxOrNull() ?: 0.0
    val min = this.minOrNull() ?: 0.0
    return min.roundDownToMultipleOf(5.0)..max.roundUpToMultipleOf(5.0)
}

fun Double.roundDownToMultipleOf(base: Double) = floor(this / base) * base

fun Double.roundUpToMultipleOf(base: Double) = ceil(this / base) * base
