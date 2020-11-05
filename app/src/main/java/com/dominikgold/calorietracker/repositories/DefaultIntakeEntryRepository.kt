package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.datasources.db.CalorieTrackerRoomDb
import com.dominikgold.calorietracker.datasources.db.PersistedIntakeEntry
import com.dominikgold.calorietracker.datasources.db.toEntity
import com.dominikgold.calorietracker.datasources.db.toPersistedModel
import com.dominikgold.calorietracker.entities.IntakeEntry
import com.dominikgold.calorietracker.usecases.intakeentries.IntakeEntryRepository
import kotlinx.coroutines.delay
import java.time.LocalDate
import javax.inject.Inject

class DefaultIntakeEntryRepository @Inject constructor(
    private val roomDb: CalorieTrackerRoomDb,
) : IntakeEntryRepository {

    override suspend fun getIntakeEntriesForToday(): List<IntakeEntry> {
        return roomDb.intakeEntryDao().getIntakeEntriesForDate(LocalDate.now()).map(PersistedIntakeEntry::toEntity)
    }

    override suspend fun saveIntakeEntry(intakeEntry: IntakeEntry) {
        roomDb.intakeEntryDao().addIntakeEntry(intakeEntry.toPersistedModel())
    }

}