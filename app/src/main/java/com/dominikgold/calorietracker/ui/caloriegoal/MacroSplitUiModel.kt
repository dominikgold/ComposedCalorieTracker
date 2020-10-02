package com.dominikgold.calorietracker.ui.caloriegoal

import androidx.annotation.StringRes
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.entities.MacroSplit
import kotlin.math.roundToInt

data class MacroSplitUiModel(private val macroSplit: MacroSplit, private val tdee: Int) {

    @StringRes
    val name = macroSplit.translatableName

    val formattedProteinAmount = "${(tdee * macroSplit.protein / 4).roundToInt()} g"
    val formattedCarbohydratesAmount = "${(tdee * macroSplit.carbohydrates / 4).roundToInt()} g"
    val formattedFatAmount = "${(tdee * macroSplit.fat / 9).roundToInt()} g"

}

val MacroSplit.translatableName: Int
    @StringRes get() = when (this) {
        MacroSplit.HIGH_CARB -> R.string.macro_split_high_carb_name
        MacroSplit.BALANCED -> R.string.macro_split_balanced_name
        MacroSplit.HIGH_FAT -> R.string.macro_split_high_fat_name
        MacroSplit.VERY_HIGH_FAT -> R.string.macro_split_very_high_fat_name
    }
