package com.dominikgold.calorietracker.ui.caloriegoal

import com.dominikgold.calorietracker.ViewModel
import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.entities.MacroSplit
import com.dominikgold.calorietracker.navigation.Navigator
import com.dominikgold.calorietracker.usecases.caloriegoal.SetCalorieGoalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SetCalorieGoalViewModel(
    private val setCalorieGoalUseCase: SetCalorieGoalUseCase,
    private val navigator: Navigator,
) : ViewModel() {

    private val _uiModelState = MutableStateFlow(SetCalorieGoalUiModel(null, null, false))
    val uiModelState: StateFlow<SetCalorieGoalUiModel>
        get() = _uiModelState

    private var chosenMacroSplit: MacroSplit? = null

    fun saveCalorieGoal() {
        val tdee = uiModelState.value.tdeeInput
        val macroSplit = chosenMacroSplit
        require(tdee != null && macroSplit != null) { "Tdee or macro split are not set when saving calorie goal" }
        coroutineScope.launch {
            setCalorieGoalUseCase.setCalorieGoal(CalorieGoal(tdee, macroSplit))
            navigator.goBack()
        }
    }

    fun updateTdee(tdee: Int?) {
        _uiModelState.value = _uiModelState.value.copy(
            tdeeInput = tdee,
            isSaveButtonEnabled = tdee != null && chosenMacroSplit != null,
        )
    }

    fun updateMacroSplit(macroSplit: MacroSplit) {
        this.chosenMacroSplit = macroSplit
        _uiModelState.value = _uiModelState.value.copy(
            chosenMacroSplit = macroSplit,
            isSaveButtonEnabled = _uiModelState.value.tdeeInput != null,
        )
    }

}

class SetCalorieGoalViewModelFactory @Inject constructor(
    private val setCalorieGoalUseCase: SetCalorieGoalUseCase,
    private val navigator: Navigator,
) : ViewModelFactory<SetCalorieGoalViewModel, Nothing, Nothing> {

    override fun create(savedState: Nothing?, parameters: Nothing?): SetCalorieGoalViewModel {
        return SetCalorieGoalViewModel(setCalorieGoalUseCase, navigator)
    }

}
