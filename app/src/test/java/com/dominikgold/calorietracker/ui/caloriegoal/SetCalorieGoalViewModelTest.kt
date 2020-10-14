package com.dominikgold.calorietracker.ui.caloriegoal

import com.dominikgold.calorietracker.entities.MacroSplit
import com.dominikgold.calorietracker.usecases.caloriegoal.SetCalorieGoalUseCase
import org.amshove.kluent.mock
import org.amshove.kluent.shouldBe
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class SetCalorieGoalViewModelTest {

    private lateinit var setCalorieGoalUseCase: SetCalorieGoalUseCase

    private lateinit var viewModel: SetCalorieGoalViewModel

    @Before
    fun setUp() {
        setCalorieGoalUseCase = mock()
        viewModel = SetCalorieGoalViewModel(setCalorieGoalUseCase)
    }

    @Test
    fun `the save button is enabled when both tdee and macro split are set`() {
        viewModel.uiModelState.value.isSaveButtonEnabled shouldBe false

        viewModel.updateTdee(2000)
        viewModel.uiModelState.value.isSaveButtonEnabled shouldBe false

        viewModel.updateMacroSplit(MacroSplit.BALANCED)
        viewModel.uiModelState.value.isSaveButtonEnabled shouldBe true

        viewModel.updateTdee(null)
        viewModel.uiModelState.value.isSaveButtonEnabled shouldBe false

        viewModel.updateTdee(2000)
        viewModel.uiModelState.value.isSaveButtonEnabled shouldBe true
    }

}