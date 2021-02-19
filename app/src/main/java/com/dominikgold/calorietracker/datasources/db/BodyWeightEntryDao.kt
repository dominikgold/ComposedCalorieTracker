package com.dominikgold.calorietracker.datasources.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.dominikgold.calorietracker.datasources.db.model.PersistedBodyWeightEntry
import com.dominikgold.calorietracker.repositories.BodyWeightEntryDataSource
import java.time.LocalDate

@Dao
interface BodyWeightEntryDao : BodyWeightEntryDataSource {

    @Query("SELECT * FROM body_weight_entries WHERE date = :forDate")
    override suspend fun getBodyWeightEntry(forDate: LocalDate): PersistedBodyWeightEntry?

    @Query("SELECT * FROM body_weight_entries WHERE date(date) >= date(:date)")
    override suspend fun getBodyWeightEntriesAfterDate(date: LocalDate): List<PersistedBodyWeightEntry>

    @Insert(onConflict = REPLACE)
    override suspend fun upsertBodyWeightEntry(bodyWeightEntry: PersistedBodyWeightEntry)

    @Query("DELETE from body_weight_entries where date = :forDate")
    override suspend fun deleteBodyWeightEntry(forDate: LocalDate)

}