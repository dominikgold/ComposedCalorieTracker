package com.dominikgold.calorietracker.entities

import kotlin.math.roundToInt

data class CalorieGoal(
    val totalCalories: Int,
    val carbohydrates: Grams?,
    val protein: Grams?,
    val fat: Grams?,
) {

    companion object {

        fun createWithMacroSplit(totalCalories: Int, macroSplit: MacroSplit) = CalorieGoal(
            totalCalories = totalCalories,
            carbohydrates = (totalCalories * macroSplit.carbohydrates / 4).roundToInt(),
            protein = (totalCalories * macroSplit.protein / 4).roundToInt(),
            fat = (totalCalories * macroSplit.fat / 9).roundToInt(),
        )

    }

}
