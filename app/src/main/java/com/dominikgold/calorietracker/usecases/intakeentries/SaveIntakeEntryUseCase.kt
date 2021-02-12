package com.dominikgold.calorietracker.usecases.intakeentries

import com.dominikgold.calorietracker.entities.IntakeEntry
import com.dominikgold.calorietracker.entities.MacroGoals
import javax.inject.Inject

interface SaveIntakeEntryUseCase {

    suspend fun saveIntakeEntry(name: String, calories: Int, macroGoals: MacroGoals): IntakeEntry

}

class DefaultSaveIntakeEntryUseCase @Inject constructor(private val intakeEntryRepository: IntakeEntryRepository) :
    SaveIntakeEntryUseCase {

    override suspend fun saveIntakeEntry(name: String, calories: Int, macroGoals: MacroGoals): IntakeEntry {
        return intakeEntryRepository.saveIntakeEntry(name, calories, macroGoals)
    }

}
