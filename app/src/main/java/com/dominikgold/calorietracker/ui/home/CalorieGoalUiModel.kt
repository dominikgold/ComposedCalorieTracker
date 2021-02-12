package com.dominikgold.calorietracker.ui.home

import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.entities.Grams
import com.dominikgold.calorietracker.entities.IntakeEntry

data class CalorieGoalUiModel(
    val totalCalories: Int,
    val caloriesLeft: Int,
    val carbohydratesGoal: MacroGoalUiModel?,
    val proteinGoal: MacroGoalUiModel?,
    val fatGoal: MacroGoalUiModel?,
) {
    val isExceeded: Boolean = caloriesLeft < 0
}

data class MacroGoalUiModel(
    val totalAmount: Grams,
    val amountLeft: Grams,
) {
    val isExceeded: Boolean = amountLeft < 0
}

fun CalorieGoalUiModel.addIntakeEntry(intakeEntry: IntakeEntry) = CalorieGoalUiModel(
    totalCalories = this.totalCalories,
    caloriesLeft = this.caloriesLeft - intakeEntry.calories,
    carbohydratesGoal = this.carbohydratesGoal?.copy(
        totalAmount = carbohydratesGoal.totalAmount,
        amountLeft = carbohydratesGoal.amountLeft - (intakeEntry.carbohydrates ?: 0),
    ),
    proteinGoal = this.proteinGoal?.copy(
        totalAmount = proteinGoal.totalAmount,
        amountLeft = proteinGoal.amountLeft - (intakeEntry.protein ?: 0),
    ),
    fatGoal = this.fatGoal?.copy(
        totalAmount = fatGoal.totalAmount,
        amountLeft = fatGoal.amountLeft - (intakeEntry.fat ?: 0),
    ),
)

fun CalorieGoalUiModel.removeIntakeEntry(uiModel: IntakeEntryUiModel) = CalorieGoalUiModel(
    totalCalories = this.totalCalories,
    caloriesLeft = this.caloriesLeft + uiModel.calories,
    carbohydratesGoal = this.carbohydratesGoal?.copy(
        totalAmount = carbohydratesGoal.totalAmount,
        amountLeft = carbohydratesGoal.amountLeft + (uiModel.carbohydrates ?: 0),
    ),
    proteinGoal = this.proteinGoal?.copy(
        totalAmount = proteinGoal.totalAmount,
        amountLeft = proteinGoal.amountLeft + (uiModel.protein ?: 0),
    ),
    fatGoal = this.fatGoal?.copy(
        totalAmount = fatGoal.totalAmount,
        amountLeft = fatGoal.amountLeft + (uiModel.fat ?: 0),
    ),
)

fun CalorieGoal.toUiModel(allIntakeEntries: List<IntakeEntry>): CalorieGoalUiModel {
    var consumedCalories = 0
    var consumedCarbohydrates = 0
    var consumedProtein = 0
    var consumedFat = 0
    allIntakeEntries.forEach {
        consumedCalories += it.calories
        consumedCarbohydrates += it.carbohydrates ?: 0
        consumedProtein += it.protein ?: 0
        consumedFat += it.fat ?: 0
    }
    return CalorieGoalUiModel(
        totalCalories = this.totalCalories,
        caloriesLeft = this.totalCalories - consumedCalories,
        carbohydratesGoal = this.macroGoals.carbohydrates?.let { MacroGoalUiModel(it, it - consumedCarbohydrates) },
        proteinGoal = this.macroGoals.protein?.let { MacroGoalUiModel(it, it - consumedProtein) },
        fatGoal = this.macroGoals.fat?.let { MacroGoalUiModel(it, it - consumedFat) },
    )
}