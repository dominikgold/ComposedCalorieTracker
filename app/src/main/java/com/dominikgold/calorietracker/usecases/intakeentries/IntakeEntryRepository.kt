package com.dominikgold.calorietracker.usecases.intakeentries

import com.dominikgold.calorietracker.entities.IntakeEntry

interface IntakeEntryRepository {

    suspend fun getIntakeEntriesForToday(): List<IntakeEntry>

    suspend fun saveIntakeEntry(intakeEntry: IntakeEntry)

}