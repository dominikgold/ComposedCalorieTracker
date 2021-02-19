package com.dominikgold.calorietracker.util

fun inputFilters(vararg filters: TextInputFilter, next: (String) -> Unit) = { input: String ->
    var currentFilterIndex = 0
    var filteredText = input
    while (currentFilterIndex < filters.size) {
        filteredText = filters[currentFilterIndex].filterInput(filteredText)
        currentFilterIndex += 1
    }
    next(filteredText)
}

interface TextInputFilter {

    fun filterInput(input: String): String

}

class LengthInputFilter(private val maxLength: Int) : TextInputFilter {

    override fun filterInput(input: String) = input.substring(0, minOf(input.length, maxLength))

}

class NaturalNumberInputFilter : TextInputFilter {

    override fun filterInput(input: String) = input.filter { it.isDigit() }

}

class DecimalNumberInputFilter : TextInputFilter {

    override fun filterInput(input: String): String {
        val decimalPointChars = charArrayOf('.', ',')
        var decimalPointFound = false
        return input.filter {
            when {
                it.isDigit() -> true
                it in decimalPointChars && !decimalPointFound -> {
                    decimalPointFound = true
                    true
                }
                else -> false
            }
        }
    }

}
