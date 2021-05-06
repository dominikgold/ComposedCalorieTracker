package com.dominikgold.calorietracker.ui.bodyweight

import com.dominikgold.calorietracker.entities.BodyWeightEntryPeriod
import com.dominikgold.calorietracker.entities.TimeInterval

data class BodyWeightHistoryUiModel(
    private val bodyWeightEntryPeriods: List<BodyWeightEntryPeriod>,
    val timeInterval: TimeInterval,
    val periodCount: Int,
) {

    val averagesForLineChart: List<Double> by lazy {
        val bodyWeightPeriodsFromOldest = bodyWeightEntryPeriods.reversed()
        val firstPeriodWithAverage = bodyWeightPeriodsFromOldest.firstOrNull { it.average != null }
        val resultList = mutableListOf<Double>()
        bodyWeightPeriodsFromOldest.forEachIndexed { index, bodyWeightEntryPeriod ->
            if (index == 0 && bodyWeightEntryPeriod.average == null) {
                resultList.add(firstPeriodWithAverage?.average ?: 0.0)
            } else if (bodyWeightEntryPeriod.average == null) {
                resultList.add(resultList[index - 1])
            } else {
                resultList.add(bodyWeightEntryPeriod.average ?: 0.0)
            }
        }
        return@lazy resultList
    }

    val unfilteredAverages: List<Double?> = bodyWeightEntryPeriods.map { it.average }

}
