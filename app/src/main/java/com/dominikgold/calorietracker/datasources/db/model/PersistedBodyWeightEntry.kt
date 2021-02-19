package com.dominikgold.calorietracker.datasources.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dominikgold.calorietracker.entities.Grams
import java.time.LocalDate

@Entity(tableName = "body_weight_entries")
data class PersistedBodyWeightEntry(
    @PrimaryKey val date: LocalDate,
    val bodyWeight: Grams,
)