package com.dominikgold.calorietracker.usecases.intakeentries

import com.dominikgold.calorietracker.entities.IntakeEntry
import javax.inject.Inject

interface GetIntakeEntriesUseCase {

    suspend fun getIntakeEntries(): List<IntakeEntry>

}

class DefaultGetIntakeEntriesUseCase @Inject constructor(private val intakeEntryRepository: IntakeEntryRepository)
    : GetIntakeEntriesUseCase {

    override suspend fun getIntakeEntries() = intakeEntryRepository.getIntakeEntries()

}
