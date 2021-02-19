package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.datasources.db.model.PersistedBodyWeightEntry
import com.dominikgold.calorietracker.entities.BodyWeightEntry
import com.dominikgold.calorietracker.usecases.bodyweight.BodyWeightEntryRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.roundToInt

class DefaultBodyWeightEntryRepository @Inject constructor(private val dataSource: BodyWeightEntryDataSource) :
    BodyWeightEntryRepository {

    override suspend fun getBodyWeightForToday(): BodyWeightEntry? {
        return dataSource.getBodyWeightEntry(forDate = LocalDate.now())?.toEntity()
    }

    override suspend fun saveBodyWeightForToday(bodyWeight: Float) {
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
        bodyWeight = (this.bodyWeight.toFloat() / 1000f),
    )

}