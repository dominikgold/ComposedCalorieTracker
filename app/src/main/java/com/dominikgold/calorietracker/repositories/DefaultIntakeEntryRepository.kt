package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.entities.IntakeEntry
import com.dominikgold.calorietracker.usecases.intakeentries.IntakeEntryRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class DefaultIntakeEntryRepository @Inject constructor() : IntakeEntryRepository {

    private var intakeEntries = mutableListOf<IntakeEntry>()

    override suspend fun getIntakeEntries(): List<IntakeEntry> {
        return intakeEntries
    }

    override fun saveIntakeEntry(intakeEntry: IntakeEntry) {
        intakeEntries.add(intakeEntry)
    }

}