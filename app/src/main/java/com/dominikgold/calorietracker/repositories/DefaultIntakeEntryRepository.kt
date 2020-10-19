package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.entities.IntakeEntry
import com.dominikgold.calorietracker.usecases.intakeentries.IntakeEntryRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class DefaultIntakeEntryRepository @Inject constructor() : IntakeEntryRepository {

    override suspend fun getIntakeEntries(): List<IntakeEntry> {
        return listOf(
                IntakeEntry("essen", 400, 20, 60, 10),
                IntakeEntry("essen", 400, 20, 60, 10),
                IntakeEntry("essen", 400, 20, 60, 10),
        )
    }

}