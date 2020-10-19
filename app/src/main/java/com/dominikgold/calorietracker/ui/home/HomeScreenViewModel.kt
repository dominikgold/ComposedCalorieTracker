package com.dominikgold.calorietracker.ui.home

import com.dominikgold.calorietracker.ViewModel
import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.entities.IntakeEntry
import com.dominikgold.calorietracker.navigation.Navigator
import com.dominikgold.calorietracker.navigation.Screen
import com.dominikgold.calorietracker.usecases.caloriegoal.GetCalorieGoalUseCase
import com.dominikgold.calorietracker.usecases.intakeentries.GetIntakeEntriesUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeScreenViewModel(
    private val navigator: Navigator,
    private val getIntakeEntriesUseCase: GetIntakeEntriesUseCase,
    private val getCalorieGoalUseCase: GetCalorieGoalUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiModel(
        showNoCalorieGoalSet = false,
        calorieGoal = null,
        intakeEntries = listOf(),
    ))

    val uiState: StateFlow<HomeScreenUiModel>
        get() = _uiState

    init {
        loadHomeScreenData()
    }

    fun reload() {
        loadHomeScreenData()
    }

    fun navigateToSetCalorieGoal() {
        navigator.switchScreen(Screen.SetCalorieGoal)
    }

    private fun loadHomeScreenData() {
        coroutineScope.launch {
            val intakeEntriesJob = async { getIntakeEntriesUseCase.getIntakeEntries() }
            val calorieGoalJob = async { getCalorieGoalUseCase.getCalorieGoal() }
            val intakeEntries = intakeEntriesJob.await()
            val calorieGoal = calorieGoalJob.await()
            if (isActive) {
                _uiState.value = HomeScreenUiModel(
                    showNoCalorieGoalSet = calorieGoal == null,
                    calorieGoal = calorieGoal?.toUiModel(intakeEntries),
                    intakeEntries = intakeEntries.map(IntakeEntry::toUiModel),
                )
            }
        }
    }

}

class HomeScreenViewModelFactory @Inject constructor(
    private val navigator: Navigator,
    private val getIntakeEntriesUseCase: GetIntakeEntriesUseCase,
    private val getCalorieGoalUseCase: GetCalorieGoalUseCase,
) : ViewModelFactory<HomeScreenViewModel, Nothing, Nothing> {

    override fun create(savedState: Nothing?, parameters: Nothing?): HomeScreenViewModel {
        return HomeScreenViewModel(navigator, getIntakeEntriesUseCase, getCalorieGoalUseCase)
    }

}
