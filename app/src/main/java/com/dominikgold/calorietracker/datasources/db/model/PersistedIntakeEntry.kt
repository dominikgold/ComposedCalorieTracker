package com.dominikgold.calorietracker.datasources.db.model

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

fun PersistedIntakeEntry.toEntity() =
    IntakeEntry(name, calories, carbohydrates.takeIf { it >= 0 }, protein.takeIf { it >= 0 }, fat.takeIf { it >= 0 })

fun IntakeEntry.toPersistedModel() =
    PersistedIntakeEntry(name, calories, carbohydrates ?: -1, protein ?: -1, fat ?: -1, LocalDate.now())
