package com.dominikgold.calorietracker.entities

import kotlin.math.roundToInt

data class CalorieGoal(
        val totalCalories: Int,
        val carbohydrates: Grams,
        val protein: Grams,
        val fat: Grams,
) {

    companion object {

        fun createWithMacroSplit(calories: Int, macroSplit: MacroSplit) =
                CalorieGoal(
                        totalCalories = calories,
                        carbohydrates = (calories * macroSplit.carbohydrates / 4).roundToInt(),
                        protein = (calories * macroSplit.protein / 4).roundToInt(),
                        fat = (calories * macroSplit.fat / 9).roundToInt(),
                )

    }

}
