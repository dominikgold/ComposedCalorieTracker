package com.dominikgold.calorietracker.ui.caloriegoal

import android.os.Parcelable
import com.dominikgold.calorietracker.entities.MacroSplit
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SetCalorieGoalUiModel(
    val tdeeInput: Int?,
    private val chosenMacroSplit: MacroSplit?,
    val isSaveButtonEnabled: Boolean,
): Parcelable {

    val macroSplitUiModel = chosenMacroSplit?.let { MacroSplitUiModel(it, tdeeInput ?: 0) }

}