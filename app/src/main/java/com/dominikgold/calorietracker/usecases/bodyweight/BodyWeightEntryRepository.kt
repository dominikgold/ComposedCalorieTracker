package com.dominikgold.calorietracker.usecases.bodyweight

import com.dominikgold.calorietracker.entities.BodyWeightEntry
import java.time.LocalDate

interface BodyWeightEntryRepository {

    suspend fun getBodyWeightEntriesAfterDate(after: LocalDate): List<BodyWeightEntry>

    suspend fun getBodyWeightForToday(): BodyWeightEntry?

    suspend fun saveBodyWeightForToday(bodyWeight: Double)

    suspend fun removeBodyWeightForToday()

}