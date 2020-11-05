package com.dominikgold.calorietracker.datasources.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dominikgold.calorietracker.entities.IntakeEntry
import java.time.LocalDate

@Entity(tableName = "intake_entries")
data class PersistedIntakeEntry(
    val name: String,
    val calories: Int,
    val carbohydrates: Int,
    val protein: Int,
    val fat: Int,
    val date: LocalDate,
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}

fun PersistedIntakeEntry.toEntity() = IntakeEntry(name, calories, carbohydrates, protein, fat)

fun IntakeEntry.toPersistedModel() = PersistedIntakeEntry(name, calories, carbohydrates, protein, fat, LocalDate.now())
