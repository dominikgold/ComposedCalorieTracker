package com.dominikgold.calorietracker.usecases.bodyweight

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SaveBodyWeightUseCase {

    fun saveBodyWeightForToday(bodyWeight: Float?)

}

class DefaultSaveBodyWeightUseCase @Inject constructor(private val repository: BodyWeightEntryRepository) :
    SaveBodyWeightUseCase {

    override fun saveBodyWeightForToday(bodyWeight: Float?) {
        GlobalScope.launch {
            try {
                if (bodyWeight == null) {
                    repository.removeBodyWeightForToday()
                } else {
                    repository.saveBodyWeightForToday(bodyWeight)
                }
            } catch (e: Throwable) {
                Log.e("SaveBodyWeight", "Failed to save body weight entry", e)
            }
        }
    }

}