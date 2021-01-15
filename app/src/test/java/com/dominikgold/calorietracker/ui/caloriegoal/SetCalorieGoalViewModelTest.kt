package com.dominikgold.calorietracker.ui.caloriegoal

import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.entities.MacroSplit
import com.dominikgold.calorietracker.navigation.Navigator
import com.dominikgold.calorietracker.usecases.caloriegoal.SetCalorieGoalUseCase
import com.dominikgold.calorietracker.util.CoroutinesTestRule
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotThrow
import org.amshove.kluent.shouldThrow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SetCalorieGoalViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var setCalorieGoalUseCase: SetCalorieGoalUseCase

    @Mock
    private lateinit var navigator: Navigator

    @InjectMocks
    private lateinit var viewModel: SetCalorieGoalViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
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

    @Test
    fun `saving the calorie goal fails if tdee or macro split is not set`() {
        val withoutEither = { viewModel.saveCalorieGoal() }
        withoutEither shouldThrow Exception::class

        viewModel.updateTdee(2000)
        val withoutMacroSplit = { viewModel.saveCalorieGoal() }
        withoutMacroSplit shouldThrow Exception::class

        viewModel.updateMacroSplit(MacroSplit.BALANCED)
        val withBoth = { viewModel.saveCalorieGoal() }
        withBoth shouldNotThrow Exception::class

        viewModel.updateTdee(null)
        val withoutTdee = { viewModel.saveCalorieGoal() }
        withoutTdee shouldThrow Exception::class
    }

    @Test
    fun `saving the calorie goal is delegated to the use case`() = runBlockingTest {
        viewModel.updateTdee(2000)
        viewModel.updateMacroSplit(MacroSplit.BALANCED)

        viewModel.saveCalorieGoal()

        verify(setCalorieGoalUseCase).setCalorieGoal(CalorieGoal.createWithMacroSplit(2000, MacroSplit.BALANCED))
    }

    @Test
    fun `navigates back after saving the calorie goal`() = runBlockingTest {
        viewModel.updateTdee(2000)
        viewModel.updateMacroSplit(MacroSplit.BALANCED)

        viewModel.saveCalorieGoal()

        verify(navigator).goBack()
    }

}