package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.datasources.db.model.PersistedBodyWeightEntry
import com.dominikgold.calorietracker.entities.BodyWeightEntry
import com.dominikgold.calorietracker.usecases.bodyweight.BodyWeightEntryRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.roundToInt

private val BodyWeightMockData = listOf(
    BodyWeightEntry(date = LocalDate.of(2021, 2, 3), bodyWeight = 90.0),
    BodyWeightEntry(date = LocalDate.of(2021, 2, 8), bodyWeight = 89.0),
    BodyWeightEntry(date = LocalDate.of(2021, 2, 11), bodyWeight = 91.0),
    BodyWeightEntry(date = LocalDate.of(2021, 2, 13), bodyWeight = 92.0),
    BodyWeightEntry(date = LocalDate.of(2021, 2, 17), bodyWeight = 90.0),
    BodyWeightEntry(date = LocalDate.of(2021, 2, 23), bodyWeight = 89.0),
    BodyWeightEntry(date = LocalDate.of(2021, 2, 26), bodyWeight = 88.0),
    BodyWeightEntry(date = LocalDate.of(2021, 2, 28), bodyWeight = 90.0),
    BodyWeightEntry(date = LocalDate.of(2021, 3, 3), bodyWeight = 91.0),
    BodyWeightEntry(date = LocalDate.of(2021, 3, 6), bodyWeight = 92.0),
    BodyWeightEntry(date = LocalDate.of(2021, 3, 8), bodyWeight = 91.0),
    BodyWeightEntry(date = LocalDate.of(2021, 3, 12), bodyWeight = 93.0),
    BodyWeightEntry(date = LocalDate.of(2021, 3, 14), bodyWeight = 92.0),
)

class DefaultBodyWeightEntryRepository @Inject constructor(private val dataSource: BodyWeightEntryDataSource) :
    BodyWeightEntryRepository {

    override suspend fun getBodyWeightEntriesAfterDate(after: LocalDate): List<BodyWeightEntry> {
        return dataSource.getBodyWeightEntriesAfterDate(after).map { it.toEntity() }
    }

    override suspend fun getBodyWeightForToday(): BodyWeightEntry? {
        return dataSource.getBodyWeightEntry(forDate = LocalDate.now())?.toEntity()
    }

    override suspend fun saveBodyWeightForToday(bodyWeight: Double) {
        dataSource.upsertBodyWeightEntry(PersistedBodyWeightEntry(
            date = LocalDate.now(),
            // TODO to support imperial measurements, the calculation has to be changed here
            bodyWeight = (bodyWeight * 1000).roundToInt(),
        ))
    }

    override suspend fun removeBodyWeightForToday() {
        dataSource.deleteBodyWeightEntry(forDate = LocalDate.now())
    }

    private fun PersistedBodyWeightEntry.toEntity() = BodyWeightEntry(
        date = this.date,
        // TODO to support imperial measurements, the calculation has to be changed here
        bodyWeight = (this.bodyWeight.toDouble() / 1000.0),
    )

}