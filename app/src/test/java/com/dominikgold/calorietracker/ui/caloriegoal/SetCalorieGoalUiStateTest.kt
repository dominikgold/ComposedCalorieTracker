package com.dominikgold.calorietracker.ui.caloriegoal

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class SetCalorieGoalUiStateTest {

    @Test
    fun `the save button is enabled when a tdee is set`() {
        SetCalorieGoalUiState(tdeeInput = null, null, null, null, null).isSaveButtonEnabled shouldBe false
        SetCalorieGoalUiState(tdeeInput = 2000, null, null, null, null).isSaveButtonEnabled shouldBe true
    }

}