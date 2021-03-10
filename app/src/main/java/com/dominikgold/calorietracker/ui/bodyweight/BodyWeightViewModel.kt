package com.dominikgold.calorietracker.ui.bodyweight

import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.usecases.bodyweight.GetBodyWeightUseCase
import com.dominikgold.calorietracker.usecases.bodyweight.SaveBodyWeightUseCase
import com.dominikgold.calorietracker.util.format
import com.dominikgold.calorietracker.util.parseLocalizedDouble
import com.dominikgold.compose.viewmodel.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class BodyWeightViewModel(
    private val saveBodyWeightUseCase: SaveBodyWeightUseCase,
    private val getBodyWeightUseCase: GetBodyWeightUseCase,
) : ViewModel() {

    private val _bodyWeightState = MutableStateFlow(BodyWeightState(""))
    val bodyWeightState: StateFlow<BodyWeightState>
        get() = _bodyWeightState

    private val bodyWeightSaveEvents = MutableSharedFlow<Double?>(onBufferOverflow = BufferOverflow.DROP_OLDEST)

    init {
        coroutineScope.launch {
            val bodyWeightForToday = getBodyWeightUseCase.getBodyWeightForToday()
            _bodyWeightState.value = BodyWeightState(bodyWeightForToday?.format() ?: "")
        }
        bodyWeightSaveEvents.debounce(500).onEach { bodyWeight ->
            saveBodyWeightUseCase.saveBodyWeightForToday(bodyWeight)
        }.launchIn(coroutineScope)
    }

    fun onBodyWeightInputChanged(input: String) {
        _bodyWeightState.value = _bodyWeightState.value.copy(bodyWeightInput = input)
        coroutineScope.launch { bodyWeightSaveEvents.emit(input.parseLocalizedDouble()) }
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
