package com.dominikgold.calorietracker.ui.home

data class HomeScreenUiModel(
    val showNoCalorieGoalSet: Boolean,
    val calorieGoal: CalorieGoalUiModel?,
    val intakeEntries: List<IntakeEntryUiModel>,
    val lastDeletedIntakeEntry: IntakeEntryUiModel? = null,
)