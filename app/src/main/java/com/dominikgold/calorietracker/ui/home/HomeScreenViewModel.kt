package com.dominikgold.calorietracker.ui.home

import com.dominikgold.calorietracker.R
import com.dominikgold.compose.viewmodel.ViewModel
import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.entities.IntakeEntry
import com.dominikgold.calorietracker.navigation.Navigator
import com.dominikgold.calorietracker.navigation.Screen
import com.dominikgold.calorietracker.usecases.caloriegoal.GetCalorieGoalUseCase
import com.dominikgold.calorietracker.usecases.intakeentries.DeleteIntakeEntryUseCase
import com.dominikgold.calorietracker.usecases.intakeentries.GetIntakeEntriesUseCase
import com.dominikgold.calorietracker.usecases.intakeentries.SaveIntakeEntryUseCase
import com.dominikgold.calorietracker.util.StringProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class HomeScreenViewModel(
    private val navigator: Navigator,
    private val getIntakeEntriesUseCase: GetIntakeEntriesUseCase,
    private val getCalorieGoalUseCase: GetCalorieGoalUseCase,
    private val saveIntakeEntryUseCase: SaveIntakeEntryUseCase,
    private val deleteIntakeEntryUseCase: DeleteIntakeEntryUseCase,
    private val stringProvider: StringProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiModel(
        showNoCalorieGoalSet = false,
        calorieGoal = null,
        intakeEntries = listOf(),
    ))

    val uiState: StateFlow<HomeScreenUiModel>
        get() = _uiState

    val greeting: String
        get() = when (LocalDateTime.now().hour) {
            in 3 until 12 -> stringProvider.getString(R.string.home_screen_greeting_morning)
            in 12 until 18 -> stringProvider.getString(R.string.home_screen_greeting_afternoon)
            else -> stringProvider.getString(R.string.home_screen_greeting_evening)
        }

    init {
        loadHomeScreenData()
    }

    fun reload() {
        loadHomeScreenData()
    }

    fun navigateToSetCalorieGoal() {
        navigator.switchScreen(Screen.SetCalorieGoal)
    }

    fun addIntakeEntry(uiModel: AddIntakeEntryUiModel) {
        require(uiModel.calories != null)
        val intakeEntry = IntakeEntry(
            id = "",
            name = uiModel.name,
            calories = uiModel.calories,
            carbohydrates = uiModel.carbohydrates,
            protein = uiModel.protein,
            fat = uiModel.fat,
        )
        coroutineScope.launch {
            saveIntakeEntryUseCase.saveIntakeEntry(intakeEntry)
        }
        _uiState.value = _uiState.value.copy(
            calorieGoal = _uiState.value.calorieGoal?.addIntakeEntry(intakeEntry),
            intakeEntries = _uiState.value.intakeEntries + intakeEntry.toUiModel(),
        )
    }

    fun deleteIntakeEntry(uiModel: IntakeEntryUiModel) {
        coroutineScope.launch {
            deleteIntakeEntryUseCase.deleteIntakeEntry(uiModel.id)
        }
        _uiState.value = _uiState.value.copy(
            calorieGoal = _uiState.value.calorieGoal?.removeIntakeEntry(uiModel),
            intakeEntries = _uiState.value.intakeEntries - uiModel,
        )
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
    private val saveIntakeEntryUseCase: SaveIntakeEntryUseCase,
    private val deleteIntakeEntryUseCase: DeleteIntakeEntryUseCase,
    private val stringProvider: StringProvider,
) : ViewModelFactory<HomeScreenViewModel, Nothing, Nothing> {

    override fun create(savedState: Nothing?, parameters: Nothing?): HomeScreenViewModel {
        return HomeScreenViewModel(navigator,
                                   getIntakeEntriesUseCase,
                                   getCalorieGoalUseCase,
                                   saveIntakeEntryUseCase,
                                   deleteIntakeEntryUseCase,
                                   stringProvider)
    }

}
