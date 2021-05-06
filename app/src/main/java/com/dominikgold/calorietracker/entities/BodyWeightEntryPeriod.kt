package com.dominikgold.calorietracker.entities

data class BodyWeightEntryPeriod(val bodyWeightEntries: List<BodyWeightEntry>) {

    val average: Double? by lazy {
        if (bodyWeightEntries.isEmpty()) {
            null
        } else {
            bodyWeightEntries.sumByDouble { it.bodyWeight } / bodyWeightEntries.size.toDouble()
        }
    }

}