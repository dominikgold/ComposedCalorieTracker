package com.dominikgold.calorietracker.util

import java.text.DecimalFormat

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