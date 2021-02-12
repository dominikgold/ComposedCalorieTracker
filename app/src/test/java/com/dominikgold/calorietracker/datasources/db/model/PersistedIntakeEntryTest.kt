package com.dominikgold.calorietracker.datasources.db.model

import com.dominikgold.calorietracker.entities.IntakeEntry
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import java.time.LocalDate

class PersistedIntakeEntryTest {

    @Test
    fun `mapping an entity to PersistedIntakeEntry uses -1 in place of null for macros`() {
        val entity = IntakeEntry(id = "",
                                 name = "lunch",
                                 calories = 500,
                                 carbohydrates = null,
                                 protein = null,
                                 fat = null)

        val persistedModel = entity.toPersistedModel()

        persistedModel.carbohydrates shouldBeEqualTo -1
        persistedModel.protein shouldBeEqualTo -1
        persistedModel.fat shouldBeEqualTo -1
    }

    @Test
    fun `mapping PersistedIntakeEntry to an entity uses null in place of -1 for macros`() {
        val persistedModel = PersistedIntakeEntry(
            name = "lunch",
            calories = 500,
            carbohydrates = -1,
            protein = -1,
            fat = 50,
            date = LocalDate.now(),
        )

        val entity = persistedModel.toEntity()

        entity.carbohydrates shouldBe null
        entity.protein shouldBe null
        entity.fat shouldBe 50
    }

}