package com.dominikgold.calorietracker.ui.bodyweight

import com.dominikgold.calorietracker.entities.BodyWeightEntryPeriod

data class BodyWeightState(
    val bodyWeightInput: String,
    val pastBodyWeightPeriods: List<BodyWeightEntryPeriod>,
)
