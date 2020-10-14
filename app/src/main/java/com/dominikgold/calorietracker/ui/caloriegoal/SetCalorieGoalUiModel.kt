package com.dominikgold.calorietracker.ui.caloriegoal

import com.dominikgold.calorietracker.entities.MacroSplit

data class SetCalorieGoalUiModel(
    val tdeeInput: Int?,
    private val chosenMacroSplit: MacroSplit?,
    val isSaveButtonEnabled: Boolean,
) {

    val macroSplitUiModel = chosenMacroSplit?.let { MacroSplitUiModel(it, tdeeInput ?: 0) }

}