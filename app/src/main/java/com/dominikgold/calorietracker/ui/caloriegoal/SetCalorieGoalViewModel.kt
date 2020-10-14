package com.dominikgold.calorietracker.ui.caloriegoal

import com.dominikgold.calorietracker.ViewModel
import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.entities.MacroSplit
import com.dominikgold.calorietracker.usecases.caloriegoal.SetCalorieGoalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SetCalorieGoalViewModel(private val setCalorieGoalUseCase: SetCalorieGoalUseCase) : ViewModel() {

    private val _uiModelState = MutableStateFlow(SetCalorieGoalUiModel(null, null, false))
    val uiModelState: StateFlow<SetCalorieGoalUiModel>
        get() = _uiModelState

    private var chosenMacroSplit: MacroSplit? = null

    fun saveCalorieGoal() {
        val tdee = uiModelState.value.tdeeInput
        val macroSplit = chosenMacroSplit
        require(tdee != null && macroSplit != null)
        setCalorieGoalUseCase.setCalorieGoal(CalorieGoal.createWithMacroSplit(tdee, macroSplit))
    }

    fun updateTdee(tdee: Int?) {
        _uiModelState.value = _uiModelState.value.copy(tdeeInput = tdee)
    }

    fun updateMacroSplit(macroSplit: MacroSplit) {
        this.chosenMacroSplit = macroSplit
        _uiModelState.value = _uiModelState.value.copy(chosenMacroSplit = macroSplit)
    }

}

class SetCalorieGoalViewModelFactory @Inject constructor(private val setCalorieGoalUseCase: SetCalorieGoalUseCase) :
    ViewModelFactory<SetCalorieGoalViewModel, Nothing, Nothing> {

    override fun create(savedState: Nothing?, parameters: Nothing?): SetCalorieGoalViewModel {
        return SetCalorieGoalViewModel(setCalorieGoalUseCase)
    }

}
