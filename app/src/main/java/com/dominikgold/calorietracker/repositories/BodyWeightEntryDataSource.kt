package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.datasources.db.model.PersistedBodyWeightEntry
import java.time.LocalDate

interface BodyWeightEntryDataSource {

    suspend fun getBodyWeightEntry(forDate: LocalDate): PersistedBodyWeightEntry?

    suspend fun getBodyWeightEntriesAfterDate(date: LocalDate): List<PersistedBodyWeightEntry>

    suspend fun upsertBodyWeightEntry(bodyWeightEntry: PersistedBodyWeightEntry)

    suspend fun deleteBodyWeightEntry(forDate: LocalDate)

}