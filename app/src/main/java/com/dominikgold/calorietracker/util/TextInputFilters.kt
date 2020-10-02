package com.dominikgold.calorietracker.util

fun LengthInputFilter(maxLength: Int, next: (String) -> Unit) = { input: String ->
    next(input.substring(0, minOf(input.length, maxLength)))
}