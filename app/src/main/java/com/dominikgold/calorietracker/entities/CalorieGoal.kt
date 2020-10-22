package com.dominikgold.calorietracker.entities

import kotlin.math.roundToInt

data class CalorieGoal(
    val totalCalories: Int,
    val macroSplit: MacroSplit,
) {

    val carbohydrates = (totalCalories * macroSplit.carbohydrates / 4).roundToInt()
    val protein = (totalCalories * macroSplit.protein / 4).roundToInt()
    val fat = (totalCalories * macroSplit.fat / 9).roundToInt()

}
