package com.dominikgold.calorietracker.datasources.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.time.LocalDate

@Dao
interface IntakeEntryDao {

    @Insert
    suspend fun addIntakeEntry(intakeEntry: PersistedIntakeEntry)

    @Query("DELETE FROM intake_entries WHERE id = :id")
    suspend fun deleteIntakeEntry(id: Int)

    @Query("SELECT * from intake_entries WHERE date = :date")
    suspend fun getIntakeEntriesForDate(date: LocalDate): List<PersistedIntakeEntry>

    @Query("SELECT * from intake_entries WHERE date(date) >= date(:date)")
    suspend fun getIntakeEntriesAfterDate(date: LocalDate): List<PersistedIntakeEntry>

}