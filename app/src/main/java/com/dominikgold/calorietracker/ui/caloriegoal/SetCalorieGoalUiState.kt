package com.dominikgold.calorietracker.ui.caloriegoal

import android.os.Parcelable
import androidx.annotation.StringRes
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.entities.Grams
import com.dominikgold.calorietracker.entities.MacroSplit
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.IgnoredOnParcel

@Parcelize
data class SetCalorieGoalUiState(
    val tdeeInput: Int?,
    val carbohydratesInput: Grams?,
    val proteinInput: Grams?,
    val fatInput: Grams?,
    val chosenMacroSplit: MacroSplit? = null,
) : Parcelable {

    @IgnoredOnParcel
    val macroSplitName = chosenMacroSplit?.translatableName

    @IgnoredOnParcel
    val isSaveButtonEnabled: Boolean
        get() = tdeeInput != null

    @IgnoredOnParcel
    val formattedProteinAmount = proteinInput?.let { "$it g" } ?: ""

    @IgnoredOnParcel
    val formattedCarbohydratesAmount = carbohydratesInput?.let { "$it g" } ?: ""

    @IgnoredOnParcel
    val formattedFatAmount = fatInput?.let { "$it g" } ?: ""

}

val MacroSplit.translatableName: Int
    @StringRes get() = when (this) {
        MacroSplit.HIGH_CARB -> R.string.macro_split_high_carb_name
        MacroSplit.BALANCED -> R.string.macro_split_balanced_name
        MacroSplit.HIGH_FAT -> R.string.macro_split_high_fat_name
        MacroSplit.VERY_HIGH_FAT -> R.string.macro_split_very_high_fat_name
    }
