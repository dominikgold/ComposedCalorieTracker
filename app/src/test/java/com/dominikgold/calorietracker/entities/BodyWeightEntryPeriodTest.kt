package com.dominikgold.calorietracker.entities

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import java.time.LocalDate

class BodyWeightEntryPeriodTest {

    @Test
    fun `average is calculated correctly`() {
        BodyWeightEntryPeriod(
            timeInterval = TimeInterval.Weekly,
            bodyWeightEntries = listOf(
                BodyWeightEntry(LocalDate.of(2021, 3, 8), 90.0),
                BodyWeightEntry(LocalDate.of(2021, 3, 5), 89.0),
                BodyWeightEntry(LocalDate.of(2021, 3, 4), 87.0),
                BodyWeightEntry(LocalDate.of(2021, 3, 3), 92.0),
            ),
        ).average shouldBeEqualTo 89.5
    }

    @Test
    fun `average is null when there are no body weight entries in the period`() {
        BodyWeightEntryPeriod(timeInterval = TimeInterval.Weekly, bodyWeightEntries = listOf()).average shouldBe null
    }

}