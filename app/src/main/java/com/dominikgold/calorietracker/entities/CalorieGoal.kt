package com.dominikgold.calorietracker.entities

import kotlin.math.roundToInt

data class CalorieGoal(
        val totalCalories: Int,
        val carbohydrates: Grams,
        val protein: Grams,
        val fat: Grams,
) {

    companion object {

        fun createWithMacroPercentages(calories: Int, carbohydrates: Percentage, protein: Percentage, fat: Percentage) =
                CalorieGoal(
                        totalCalories = calories,
                        carbohydrates = (calories * carbohydrates / 4).roundToInt(),
                        protein = (calories * protein / 4).roundToInt(),
                        fat = (calories * fat / 9).roundToInt(),
                )

    }

}
