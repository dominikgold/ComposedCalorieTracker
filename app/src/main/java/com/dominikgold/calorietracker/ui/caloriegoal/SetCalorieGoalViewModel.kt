package com.dominikgold.calorietracker.ui.caloriegoal

import com.dominikgold.calorietracker.ViewModel
import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.entities.Grams
import com.dominikgold.calorietracker.entities.MacroGoals
import com.dominikgold.calorietracker.entities.MacroSplit
import com.dominikgold.calorietracker.navigation.Navigator
import com.dominikgold.calorietracker.usecases.caloriegoal.SetCalorieGoalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class SetCalorieGoalViewModel(
    private val setCalorieGoalUseCase: SetCalorieGoalUseCase,
    private val navigator: Navigator,
    savedState: SetCalorieGoalUiState?,
) : ViewModel() {

    private val _uiState = MutableStateFlow(savedState ?: SetCalorieGoalUiState(null, null, null, null, null))
    val uiState: StateFlow<SetCalorieGoalUiState>
        get() = _uiState

    fun saveCalorieGoal() {
        val tdee = uiState.value.tdeeInput
        require(tdee != null) { "Tdee is not set when saving calorie goal" }
        coroutineScope.launch {
            setCalorieGoalUseCase.setCalorieGoal(CalorieGoal(
                totalCalories = tdee,
                macroGoals = MacroGoals(
                    carbohydrates = uiState.value.carbohydratesInput,
                    protein = uiState.value.proteinInput,
                    fat = uiState.value.fatInput,
                ),
            ))
            navigator.goBack()
        }
    }

    fun updateTdee(tdee: Int?) {
        val chosenMacroSplit = uiState.value.chosenMacroSplit
        if (chosenMacroSplit != null) {
            val macroGoals = tdee?.let { MacroGoals.createWithMacroSplit(it, chosenMacroSplit) }
            _uiState.value = _uiState.value.copy(
                tdeeInput = tdee,
                carbohydratesInput = macroGoals?.carbohydrates,
                proteinInput = macroGoals?.protein,
                fatInput = macroGoals?.fat,
            )
            _uiState.value = _uiState.value.copy(tdeeInput = tdee)
        } else {
            _uiState.value = _uiState.value.copy(tdeeInput = tdee)
        }
    }

    fun updateChosenMacroSplit(macroSplit: MacroSplit?) {
        if (macroSplit != null) {
            val macroGoals = uiState.value.tdeeInput?.let { tdee -> MacroGoals.createWithMacroSplit(tdee, macroSplit) }
            _uiState.value = _uiState.value.copy(
                chosenMacroSplit = macroSplit,
                carbohydratesInput = macroGoals?.carbohydrates,
                proteinInput = macroGoals?.protein,
                fatInput = macroGoals?.fat,
            )
        } else {
            _uiState.value = _uiState.value.copy(
                carbohydratesInput = null,
                proteinInput = null,
                fatInput = null,
                chosenMacroSplit = null,
            )
        }
    }

}

class SetCalorieGoalViewModelFactory @Inject constructor(
    private val setCalorieGoalUseCase: SetCalorieGoalUseCase,
    private val navigator: Navigator,
) : ViewModelFactory<SetCalorieGoalViewModel, SetCalorieGoalUiState, Nothing> {

    override fun create(savedState: SetCalorieGoalUiState?, parameters: Nothing?): SetCalorieGoalViewModel {
        return SetCalorieGoalViewModel(setCalorieGoalUseCase, navigator, savedState)
    }

}
