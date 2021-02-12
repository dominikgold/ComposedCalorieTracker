package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.datasources.db.model.PersistedIntakeEntry
import com.dominikgold.calorietracker.datasources.db.model.toEntity
import com.dominikgold.calorietracker.entities.IntakeEntry
import com.dominikgold.calorietracker.entities.MacroGoals
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
        intakeEntryDataSource.deleteIntakeEntry(id.toLong())
    }

    override suspend fun saveIntakeEntry(name: String, calories: Int, macroGoals: MacroGoals): IntakeEntry {
        val persistedIntakeEntry = PersistedIntakeEntry(
            name = name,
            calories = calories,
            carbohydrates = macroGoals.carbohydrates ?: -1,
            protein = macroGoals.protein ?: -1,
            fat = macroGoals.fat ?: -1,
            date = LocalDate.now(),
        )
        val id = intakeEntryDataSource.addIntakeEntry(persistedIntakeEntry)
        return intakeEntryDataSource.getIntakeEntryById(id).toEntity()
    }

}