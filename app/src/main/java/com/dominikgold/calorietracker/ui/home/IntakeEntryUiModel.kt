package com.dominikgold.calorietracker.ui.home

import com.dominikgold.calorietracker.entities.Grams
import com.dominikgold.calorietracker.entities.IntakeEntry

data class IntakeEntryUiModel(
    val name: String,
    val calories: Int,
    val carbohydrates: Grams,
    val protein: Grams,
    val fat: Grams,
)

fun IntakeEntry.toUiModel() = IntakeEntryUiModel(
    name = name,
    calories = calories,
    carbohydrates = carbohydrates,
    protein = protein,
    fat = fat,
)
