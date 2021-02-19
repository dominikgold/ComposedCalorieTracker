package com.dominikgold.calorietracker.usecases.bodyweight

import javax.inject.Inject

interface GetBodyWeightUseCase {

    suspend fun getBodyWeightForToday(): Float?

}

class DefaultGetBodyWeightUseCase @Inject constructor(private val repository: BodyWeightEntryRepository) :
    GetBodyWeightUseCase {

    override suspend fun getBodyWeightForToday(): Float? {
        return repository.getBodyWeightForToday()?.bodyWeight
    }

}