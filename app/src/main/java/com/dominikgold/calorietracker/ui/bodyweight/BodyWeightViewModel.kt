package com.dominikgold.calorietracker.ui.bodyweight

import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.usecases.bodyweight.GetBodyWeightUseCase
import com.dominikgold.calorietracker.usecases.bodyweight.SaveBodyWeightUseCase
import com.dominikgold.calorietracker.util.format
import com.dominikgold.calorietracker.util.parseLocalizedFloat
import com.dominikgold.compose.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BodyWeightViewModel(
    private val saveBodyWeightUseCase: SaveBodyWeightUseCase,
    private val getBodyWeightUseCase: GetBodyWeightUseCase,
) : ViewModel() {

    private val _bodyWeightState = MutableStateFlow(BodyWeightState(""))
    val bodyWeightState: StateFlow<BodyWeightState>
        get() = _bodyWeightState

    init {
        coroutineScope.launch {
            val bodyWeightForToday = getBodyWeightUseCase.getBodyWeightForToday()
            _bodyWeightState.value = BodyWeightState(bodyWeightForToday?.format() ?: "")
        }
    }

    fun onBodyWeightInputChanged(input: String) {
        _bodyWeightState.value = _bodyWeightState.value.copy(bodyWeightInput = input)
    }

    fun saveBodyWeight() {
        saveBodyWeightUseCase.saveBodyWeightForToday(bodyWeightState.value.bodyWeightInput.parseLocalizedFloat())
    }

}

class BodyWeightViewModelFactory @Inject constructor(
    private val saveBodyWeightUseCase: SaveBodyWeightUseCase,
    private val getBodyWeightUseCase: GetBodyWeightUseCase,
) : ViewModelFactory<BodyWeightViewModel, Nothing, Nothing> {

    override fun create(savedState: Nothing?, parameters: Nothing?): BodyWeightViewModel {
        return BodyWeightViewModel(saveBodyWeightUseCase, getBodyWeightUseCase)
    }

}
