package com.dominikgold.calorietracker.ui.home

import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.entities.Grams
import com.dominikgold.calorietracker.entities.IntakeEntry

data class CalorieGoalUiModel(
    val totalCalories: Int,
    val caloriesLeft: Int,
    val totalCarbohydrates: Grams,
    val carbohydratesLeft: Grams,
    val totalProtein: Grams,
    val proteinLeft: Grams,
    val totalFat: Grams,
    val fatLeft: Grams,
)

fun CalorieGoal.toUiModel(allIntakeEntries: List<IntakeEntry>): CalorieGoalUiModel {
    var consumedCalories = 0
    var consumedCarbohydrates = 0
    var consumedProtein = 0
    var consumedFat = 0
    allIntakeEntries.forEach {
        consumedCalories += it.calories
        consumedCarbohydrates += it.carbohydrates
        consumedProtein += it.protein
        consumedFat += it.fat
    }
    return CalorieGoalUiModel(
        totalCalories = this.totalCalories,
        caloriesLeft = this.totalCalories - consumedCalories,
        totalCarbohydrates = this.carbohydrates,
        carbohydratesLeft = this.carbohydrates - consumedCarbohydrates,
        totalProtein = this.protein,
        proteinLeft = this.protein - consumedProtein,
        totalFat = this.fat,
        fatLeft = this.fat - consumedFat,
    )
}