package com.dominikgold.calorietracker.usecases.bodyweight

import com.dominikgold.calorietracker.entities.BodyWeightEntry

interface BodyWeightEntryRepository {

    suspend fun getBodyWeightForToday(): BodyWeightEntry?

    suspend fun saveBodyWeightForToday(bodyWeight: Float)

    suspend fun removeBodyWeightForToday()

}