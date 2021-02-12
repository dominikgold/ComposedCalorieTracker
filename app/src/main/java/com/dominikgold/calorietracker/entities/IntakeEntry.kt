package com.dominikgold.calorietracker.entities

data class IntakeEntry(
        val id: String,
        val name: String,
        val calories: Int,
        val carbohydrates: Grams?,
        val protein: Grams?,
        val fat: Grams?,
)
