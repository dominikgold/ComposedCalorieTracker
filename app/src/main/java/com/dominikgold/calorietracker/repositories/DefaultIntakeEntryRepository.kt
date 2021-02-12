package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.datasources.db.model.PersistedIntakeEntry
import com.dominikgold.calorietracker.datasources.db.model.toEntity
import com.dominikgold.calorietracker.datasources.db.model.toPersistedModel
import com.dominikgold.calorietracker.entities.IntakeEntry
import com.dominikgold.calorietracker.usecases.intakeentries.IntakeEntryRepository
import java.time.LocalDate
import javax.inject.Inject

class DefaultIntakeEntryRepository @Inject constructor(
    private val intakeEntryDataSource: IntakeEntryDataSource,
) : IntakeEntryRepository {

    override suspend fun getIntakeEntriesForToday(): List<IntakeEntry> {
        return intakeEntryDataSource.getIntakeEntriesForDate(LocalDate.now()).map(PersistedIntakeEntry::toEntity)
    }

    override suspend fun deleteIntakeEntry(id: String) {
        intakeEntryDataSource.deleteIntakeEntry(id.toInt())
    }

    override suspend fun saveIntakeEntry(intakeEntry: IntakeEntry) {
        intakeEntryDataSource.addIntakeEntry(intakeEntry.toPersistedModel())
    }

}