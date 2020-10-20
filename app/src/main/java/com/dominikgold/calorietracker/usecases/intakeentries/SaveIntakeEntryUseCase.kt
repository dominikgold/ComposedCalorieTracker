package com.dominikgold.calorietracker.usecases.intakeentries

import com.dominikgold.calorietracker.entities.IntakeEntry
import javax.inject.Inject

interface SaveIntakeEntryUseCase {

    fun saveIntakeEntry(intakeEntry: IntakeEntry)

}

class DefaultSaveIntakeEntryUseCase @Inject constructor(private val intakeEntryRepository: IntakeEntryRepository) :
    SaveIntakeEntryUseCase {

    override fun saveIntakeEntry(intakeEntry: IntakeEntry) {
        intakeEntryRepository.saveIntakeEntry(intakeEntry)
    }

}
