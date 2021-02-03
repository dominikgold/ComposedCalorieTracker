package com.dominikgold.calorietracker.ui.caloriegoal

import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.entities.MacroGoals
import com.dominikgold.calorietracker.entities.MacroSplit
import com.dominikgold.calorietracker.navigation.Navigator
import com.dominikgold.calorietracker.usecases.caloriegoal.SetCalorieGoalUseCase
import com.dominikgold.calorietracker.util.CoroutinesTestRule
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
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
    fun `saving the calorie goal fails if tdee is not set`() {
        val withoutTdee = { viewModel.saveCalorieGoal() }
        withoutTdee shouldThrow Exception::class

        viewModel.updateTdee(2000)
        val withTdee = { viewModel.saveCalorieGoal() }
        withTdee shouldNotThrow Exception::class
    }

    @Test
    fun `saving the calorie goal is delegated to the use case`() = runBlockingTest {
        viewModel.updateTdee(2000)

        viewModel.saveCalorieGoal()

        verify(setCalorieGoalUseCase).setCalorieGoal(CalorieGoal(2000, MacroGoals(null, null, null)))
    }

    @Test
    fun `saving the calorie goal with a chosen macro split saves correct macro goals`() = runBlockingTest {
        viewModel.updateTdee(2000)
        viewModel.updateChosenMacroSplit(MacroSplit.BALANCED)

        viewModel.saveCalorieGoal()

        val expectedMacroGoals = MacroGoals.createWithMacroSplit(2000, MacroSplit.BALANCED)
        verify(setCalorieGoalUseCase).setCalorieGoal(CalorieGoal(2000, expectedMacroGoals))
    }

    @Test
    fun `updating macro split updates the macro values according to tdee`() {
        viewModel.updateTdee(2000)

        viewModel.updateChosenMacroSplit(MacroSplit.BALANCED)

        val expectedMacroGoals = MacroGoals.createWithMacroSplit(2000, MacroSplit.BALANCED)
        viewModel.uiState.value.carbohydratesInput shouldBeEqualTo expectedMacroGoals.carbohydrates
        viewModel.uiState.value.proteinInput shouldBeEqualTo expectedMacroGoals.protein
        viewModel.uiState.value.fatInput shouldBeEqualTo expectedMacroGoals.fat
    }

    @Test
    fun `clearing chosen macro split updates the macro values to null`() {
        viewModel.updateTdee(2000)

        viewModel.updateChosenMacroSplit(null)

        viewModel.uiState.value.carbohydratesInput shouldBe null
        viewModel.uiState.value.proteinInput shouldBe null
        viewModel.uiState.value.fatInput shouldBe null
    }

    @Test
    fun `updating tdee after choosing a macro split updates the macro values`() {
        viewModel.updateChosenMacroSplit(MacroSplit.BALANCED)

        viewModel.updateTdee(2000)

        val expectedMacroGoals = MacroGoals.createWithMacroSplit(2000, MacroSplit.BALANCED)
        viewModel.uiState.value.carbohydratesInput shouldBeEqualTo expectedMacroGoals.carbohydrates
        viewModel.uiState.value.proteinInput shouldBeEqualTo expectedMacroGoals.protein
        viewModel.uiState.value.fatInput shouldBeEqualTo expectedMacroGoals.fat
    }

    @Test
    fun `clearing tdee after choosing a macro split resets macro values to null`() {
        viewModel.updateTdee(2000)
        viewModel.updateChosenMacroSplit(MacroSplit.BALANCED)

        viewModel.updateTdee(null)

        viewModel.uiState.value.carbohydratesInput shouldBe null
        viewModel.uiState.value.proteinInput shouldBe null
        viewModel.uiState.value.fatInput shouldBe null
    }

    @Test
    fun `navigates back after saving the calorie goal`() = runBlockingTest {
        viewModel.updateTdee(2000)
        viewModel.updateChosenMacroSplit(MacroSplit.BALANCED)

        viewModel.saveCalorieGoal()

        verify(navigator).goBack()
    }

    @Test
    fun `updating protein updates protein value in ui state`() {
        viewModel.updateProtein(100)
        viewModel.uiState.value.proteinInput shouldBeEqualTo 100

        viewModel.updateProtein(null)
        viewModel.uiState.value.proteinInput shouldBe null
    }

    @Test
    fun `saving calorie goal uses specifically chosen protein amount`() = runBlockingTest {
        viewModel.updateTdee(2000)
        viewModel.updateProtein(100)

        viewModel.saveCalorieGoal()

        val expectedMacroGoals = MacroGoals(protein = 100, carbohydrates = null, fat = null)
        verify(setCalorieGoalUseCase).setCalorieGoal(CalorieGoal(2000, expectedMacroGoals))
    }

    @Test
    fun `updating carbohydrates updates carbohydrates value in ui state`() {
        viewModel.updateCarbohydrates(100)
        viewModel.uiState.value.carbohydratesInput shouldBeEqualTo 100

        viewModel.updateCarbohydrates(null)
        viewModel.uiState.value.carbohydratesInput shouldBe null
    }

    @Test
    fun `saving calorie goal uses specifically chosen carbohydrates amount`() = runBlockingTest {
        viewModel.updateTdee(2000)
        viewModel.updateCarbohydrates(100)

        viewModel.saveCalorieGoal()

        val expectedMacroGoals = MacroGoals(carbohydrates = 100, protein = null, fat = null)
        verify(setCalorieGoalUseCase).setCalorieGoal(CalorieGoal(2000, expectedMacroGoals))
    }

    @Test
    fun `updating fat updates fat value in ui state`() {
        viewModel.updateFat(100)
        viewModel.uiState.value.fatInput shouldBeEqualTo 100

        viewModel.updateFat(null)
        viewModel.uiState.value.fatInput shouldBe null
    }

    @Test
    fun `saving calorie goal uses specifically chosen fat amount`() = runBlockingTest {
        viewModel.updateTdee(2000)
        viewModel.updateFat(100)

        viewModel.saveCalorieGoal()

        val expectedMacroGoals = MacroGoals(fat = 100, carbohydrates = null, protein = null)
        verify(setCalorieGoalUseCase).setCalorieGoal(CalorieGoal(2000, expectedMacroGoals))
    }

}