package com.dominikgold.calorietracker.ui.caloriegoal

import com.dominikgold.calorietracker.entities.Grams
import com.dominikgold.calorietracker.entities.MacroSplit

interface SetCalorieGoalActions {

    fun updateTdee(tdee: Int?)

    fun updateChosenMacroSplit(macroSplit: MacroSplit?)

    fun updateProtein(protein: Grams?)

    fun updateCarbohydrates(carbohydrates: Grams?)

    fun updateFat(fat: Grams?)

}