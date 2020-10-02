package com.dominikgold.calorietracker.entities

data class IntakeEntry(
        val name: String,
        val calories: Int,
        val carbohydrates: Grams,
        val protein: Grams,
        val fat: Grams,
)
