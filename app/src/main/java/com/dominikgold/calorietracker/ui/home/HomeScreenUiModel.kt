package com.dominikgold.calorietracker.ui.home

data class HomeScreenUiModel(
    val showNoCalorieGoalSet: Boolean,
    val calorieGoal: CalorieGoalUiModel?,
    val currentIntakeEntries: List<IntakeEntryUiModel>,
    val previousIntakeEntries: List<IntakeEntryUiModel> = listOf(),
    val lastDeletedIntakeEntry: IntakeEntryUiModel? = null,
)