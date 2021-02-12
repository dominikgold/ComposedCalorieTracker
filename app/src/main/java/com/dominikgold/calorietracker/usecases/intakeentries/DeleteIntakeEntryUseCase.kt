package com.dominikgold.calorietracker.usecases.intakeentries

import android.util.Log
import javax.inject.Inject

interface DeleteIntakeEntryUseCase {

    suspend fun deleteIntakeEntry(id: String)

}

class DefaultDeleteIntakeEntryUseCase @Inject constructor(private val intakeEntryRepository: IntakeEntryRepository) :
    DeleteIntakeEntryUseCase {

    override suspend fun deleteIntakeEntry(id: String) {
        try {
            intakeEntryRepository.deleteIntakeEntry(id)
        } catch (e: Throwable) {
            Log.e("DeleteIntakeEntry", "failed to delete intake entry with id $id", e)
        }
    }

}