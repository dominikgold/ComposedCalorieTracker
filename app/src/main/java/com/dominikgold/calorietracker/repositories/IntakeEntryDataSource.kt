package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.datasources.db.model.PersistedIntakeEntry
import java.time.LocalDate

interface IntakeEntryDataSource {

    suspend fun addIntakeEntry(intakeEntry: PersistedIntakeEntry)

    suspend fun deleteIntakeEntry(id: Int)

    suspend fun getIntakeEntriesForDate(date: LocalDate): List<PersistedIntakeEntry>

    suspend fun getIntakeEntriesAfterDate(date: LocalDate): List<PersistedIntakeEntry>

}