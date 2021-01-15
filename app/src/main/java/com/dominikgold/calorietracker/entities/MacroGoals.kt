package com.dominikgold.calorietracker.entities

import kotlin.math.roundToInt

data class MacroGoals(val carbohydrates: Grams?, val protein: Grams?, val fat: Grams?) {

    companion object {

        fun createWithMacroSplit(totalCalories: Int, macroSplit: MacroSplit) = MacroGoals(
            carbohydrates = (totalCalories * macroSplit.carbohydrates / 4).roundToInt(),
            protein = (totalCalories * macroSplit.protein / 4).roundToInt(),
            fat = (totalCalories * macroSplit.fat / 9).roundToInt(),
        )

    }

}