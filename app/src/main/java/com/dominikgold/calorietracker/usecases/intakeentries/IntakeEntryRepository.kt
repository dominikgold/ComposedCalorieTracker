package com.dominikgold.calorietracker.usecases.intakeentries

import com.dominikgold.calorietracker.entities.IntakeEntry
import com.dominikgold.calorietracker.entities.MacroGoals

interface IntakeEntryRepository {

    suspend fun getIntakeEntriesForToday(): List<IntakeEntry>

    suspend fun saveIntakeEntry(name: String, calories: Int, macroGoals: MacroGoals): IntakeEntry

    suspend fun deleteIntakeEntry(id: String)

}