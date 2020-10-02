package com.dominikgold.calorietracker.usecases.intakeentries

import com.dominikgold.calorietracker.entities.IntakeEntry

interface IntakeEntryRepository {

    suspend fun getIntakeEntries(): List<IntakeEntry>

}