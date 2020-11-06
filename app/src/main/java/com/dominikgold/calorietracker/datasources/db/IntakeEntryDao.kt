package com.dominikgold.calorietracker.datasources.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dominikgold.calorietracker.datasources.db.model.PersistedIntakeEntry
import com.dominikgold.calorietracker.repositories.IntakeEntryDataSource
import java.time.LocalDate

@Dao
interface IntakeEntryDao: IntakeEntryDataSource {

    @Insert
    override suspend fun addIntakeEntry(intakeEntry: PersistedIntakeEntry)

    @Query("DELETE FROM intake_entries WHERE id = :id")
    override suspend fun deleteIntakeEntry(id: Int)

    @Query("SELECT * from intake_entries WHERE date = :date")
    override suspend fun getIntakeEntriesForDate(date: LocalDate): List<PersistedIntakeEntry>

    @Query("SELECT * from intake_entries WHERE date(date) >= date(:date)")
    override suspend fun getIntakeEntriesAfterDate(date: LocalDate): List<PersistedIntakeEntry>

}