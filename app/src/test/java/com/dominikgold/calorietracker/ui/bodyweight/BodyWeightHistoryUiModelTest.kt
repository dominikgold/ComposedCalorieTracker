package com.dominikgold.calorietracker.ui.bodyweight

import com.dominikgold.calorietracker.entities.BodyWeightEntry
import com.dominikgold.calorietracker.entities.BodyWeightEntryPeriod
import com.dominikgold.calorietracker.entities.TimeInterval
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import java.time.LocalDate

class BodyWeightHistoryUiModelTest {

    @Test
    fun `averages for line chart are in reversed order`() {
        val testBodyWeightEntryPeriods = listOf(
            BodyWeightEntryPeriod(listOf(BodyWeightEntry(LocalDate.now(), bodyWeight = 90.0))),
            BodyWeightEntryPeriod(listOf(BodyWeightEntry(LocalDate.now(), bodyWeight = 100.0))),
            BodyWeightEntryPeriod(listOf(BodyWeightEntry(LocalDate.now(), bodyWeight = 110.0))),
            BodyWeightEntryPeriod(listOf(BodyWeightEntry(LocalDate.now(), bodyWeight = 100.0))),
        )

        val bodyWeightHistory = BodyWeightHistoryUiModel(testBodyWeightEntryPeriods, TimeInterval.Weekly, 4)

        bodyWeightHistory.averagesForLineChart.size shouldBeEqualTo testBodyWeightEntryPeriods.size
        bodyWeightHistory.averagesForLineChart shouldBeEqualTo listOf(100.0, 110.0, 100.0, 90.0)
    }

    @Test
    fun `averages for line chart use the average from previous period for periods without an entry`() {
        val testBodyWeightEntryPeriods = listOf(
            BodyWeightEntryPeriod(listOf()),
            BodyWeightEntryPeriod(listOf(BodyWeightEntry(LocalDate.now(), bodyWeight = 90.0))),
            BodyWeightEntryPeriod(listOf()),
            BodyWeightEntryPeriod(listOf(BodyWeightEntry(LocalDate.now(), bodyWeight = 100.0))),
        )

        val bodyWeightHistory = BodyWeightHistoryUiModel(testBodyWeightEntryPeriods, TimeInterval.Weekly, 4)

        bodyWeightHistory.averagesForLineChart.size shouldBeEqualTo testBodyWeightEntryPeriods.size
        bodyWeightHistory.averagesForLineChart shouldBeEqualTo listOf(100.0, 100.0, 90.0, 90.0)
    }

    @Test
    fun `averages for line chart uses average from first oldest period with an average if the oldest has no entries`() {
        val testBodyWeightEntryPeriods = listOf(
            BodyWeightEntryPeriod(listOf(BodyWeightEntry(LocalDate.now(), bodyWeight = 100.0))),
            BodyWeightEntryPeriod(listOf(BodyWeightEntry(LocalDate.now(), bodyWeight = 90.0))),
            BodyWeightEntryPeriod(listOf()),
            BodyWeightEntryPeriod(listOf()),
        )

        val bodyWeightHistory = BodyWeightHistoryUiModel(testBodyWeightEntryPeriods, TimeInterval.Weekly, 4)

        bodyWeightHistory.averagesForLineChart.size shouldBeEqualTo testBodyWeightEntryPeriods.size
        bodyWeightHistory.averagesForLineChart shouldBeEqualTo listOf(90.0, 90.0, 90.0, 100.0)
    }

    @Test
    fun `averages for line chart uses 0 for all periods if none have an entry`() {
        val testBodyWeightEntryPeriods = listOf(
            BodyWeightEntryPeriod(listOf()),
            BodyWeightEntryPeriod(listOf()),
            BodyWeightEntryPeriod(listOf()),
            BodyWeightEntryPeriod(listOf()),
        )

        val bodyWeightHistory = BodyWeightHistoryUiModel(testBodyWeightEntryPeriods, TimeInterval.Weekly, 4)

        bodyWeightHistory.averagesForLineChart.size shouldBeEqualTo testBodyWeightEntryPeriods.size
        bodyWeightHistory.averagesForLineChart shouldBeEqualTo listOf(0.0, 0.0, 0.0, 0.0)
    }

}