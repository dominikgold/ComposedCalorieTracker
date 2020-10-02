package com.dominikgold.calorietracker.usecases.intakeentries

import com.dominikgold.calorietracker.entities.IntakeEntry
import javax.inject.Inject

interface LoadIntakeEntriesUseCase {

    suspend fun loadIntakeEntries(): List<IntakeEntry>

}

class DefaultLoadIntakeEntriesUseCase @Inject constructor(private val intakeEntryRepository: IntakeEntryRepository)
    : LoadIntakeEntriesUseCase {

    override suspend fun loadIntakeEntries() = intakeEntryRepository.getIntakeEntries()

}
