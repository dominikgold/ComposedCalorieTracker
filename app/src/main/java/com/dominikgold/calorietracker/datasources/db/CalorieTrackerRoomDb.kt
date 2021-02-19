package com.dominikgold.calorietracker.datasources.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dominikgold.calorietracker.datasources.db.model.PersistedBodyWeightEntry
import com.dominikgold.calorietracker.datasources.db.model.PersistedIntakeEntry

@Database(entities = [PersistedIntakeEntry::class, PersistedBodyWeightEntry::class], version = 2)
@TypeConverters(RoomTypeConverters::class)
abstract class CalorieTrackerRoomDb : RoomDatabase() {

    abstract fun intakeEntryDao(): IntakeEntryDao

    abstract fun bodyWeightEntryDao(): BodyWeightEntryDao

}