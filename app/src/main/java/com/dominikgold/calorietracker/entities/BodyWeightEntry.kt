package com.dominikgold.calorietracker.entities

import java.time.LocalDate

/**
 * @param bodyWeight body weight value associated with this entry, either in kilograms or pounds depending on which
 *  measurement system is currently preferred by the user
 */
data class BodyWeightEntry(
    val date: LocalDate,
    val bodyWeight: Double,
)