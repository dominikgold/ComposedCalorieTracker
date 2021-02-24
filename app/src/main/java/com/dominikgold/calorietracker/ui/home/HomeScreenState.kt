package com.dominikgold.calorietracker.ui.home

data class HomeScreenState(
    val showNoCalorieGoalSet: Boolean,
    val calorieGoal: CalorieGoalUiModel?,
    val currentIntakeEntries: List<IntakeEntryUiModel>,
    val previousIntakeEntries: List<IntakeEntryUiModel> = listOf(),
    val lastDeletedIntakeEntry: IntakeEntryUiModel? = null,
)