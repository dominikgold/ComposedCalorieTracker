package com.dominikgold.calorietracker.ui.home

import com.dominikgold.calorietracker.R
import com.dominikgold.compose.viewmodel.ViewModel
import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.entities.IntakeEntry
import com.dominikgold.calorietracker.entities.MacroGoals
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
        currentIntakeEntries = listOf(),
    ))

    val uiState: StateFlow<HomeScreenUiModel>
        get() = _uiState

    val greeting: String
        get() = when (LocalDateTime.now().hour) {
            in 3 until 12 -> stringProvider.getString(R.string.home_screen_greeting_morning)
            in 12 until 18 -> stringProvider.getString(R.string.home_screen_greeting_afternoon)
            else -> stringProvider.getString(R.string.home_screen_greeting_evening)
        }

    private var hasLoadedInitially = false

    fun reload() {
        loadHomeScreenData()
    }

    private fun loadHomeScreenData() {
        coroutineScope.launch {
            val intakeEntriesJob = async { getIntakeEntriesUseCase.getIntakeEntries() }
            val calorieGoalJob = async { getCalorieGoalUseCase.getCalorieGoal() }
            val intakeEntries = intakeEntriesJob.await()
            val calorieGoal = calorieGoalJob.await()
            if (isActive) {
                val previousIntakeEntries = if (hasLoadedInitially) _uiState.value.currentIntakeEntries else listOf()
                _uiState.value = HomeScreenUiModel(
                    showNoCalorieGoalSet = calorieGoal == null,
                    calorieGoal = calorieGoal?.toUiModel(intakeEntries),
                    lastDeletedIntakeEntry = null,
                    previousIntakeEntries = previousIntakeEntries,
                    currentIntakeEntries = intakeEntries.map(IntakeEntry::toUiModel),
                )
                hasLoadedInitially = true
            }
        }
    }

    fun navigateToSetCalorieGoal() {
        navigator.switchScreen(Screen.SetCalorieGoal)
    }

    fun addIntakeEntry(uiModel: AddIntakeEntryUiModel) {
        require(uiModel.calories != null)
        coroutineScope.launch {
            val savedIntakeEntry = saveIntakeEntryUseCase.saveIntakeEntry(
                name = uiModel.name,
                calories = uiModel.calories,
                macroGoals = MacroGoals(uiModel.carbohydrates, uiModel.protein, uiModel.fat),
            )
            _uiState.value = _uiState.value.copy(
                calorieGoal = _uiState.value.calorieGoal?.addIntakeEntry(savedIntakeEntry),
                previousIntakeEntries = _uiState.value.currentIntakeEntries,
                currentIntakeEntries = _uiState.value.currentIntakeEntries + savedIntakeEntry.toUiModel(),
            )
        }
    }

    fun deleteIntakeEntry(uiModel: IntakeEntryUiModel) {
        coroutineScope.launch {
            deleteIntakeEntryUseCase.deleteIntakeEntry(uiModel.id)
        }
        _uiState.value = _uiState.value.copy(
            calorieGoal = _uiState.value.calorieGoal?.removeIntakeEntry(uiModel),
            previousIntakeEntries = _uiState.value.currentIntakeEntries,
            currentIntakeEntries = _uiState.value.currentIntakeEntries - uiModel,
            lastDeletedIntakeEntry = uiModel,
        )
    }

    fun resetLastDeletedIntakeEntry() {
        _uiState.value = _uiState.value.copy(lastDeletedIntakeEntry = null)
    }

    fun undoIntakeEntryDeletion() {
        val lastDeletedIntakeEntry = uiState.value.lastDeletedIntakeEntry
        require(lastDeletedIntakeEntry != null)
        coroutineScope.launch {
            val savedIntakeEntry = saveIntakeEntryUseCase.saveIntakeEntry(
                name = lastDeletedIntakeEntry.name,
                calories = lastDeletedIntakeEntry.calories,
                macroGoals = MacroGoals(
                    carbohydrates = lastDeletedIntakeEntry.carbohydrates,
                    protein = lastDeletedIntakeEntry.protein,
                    fat = lastDeletedIntakeEntry.fat,
                ),
            )
            _uiState.value = _uiState.value.copy(
                calorieGoal = _uiState.value.calorieGoal?.addIntakeEntry(savedIntakeEntry),
                previousIntakeEntries = _uiState.value.currentIntakeEntries,
                currentIntakeEntries = _uiState.value.currentIntakeEntries + savedIntakeEntry.toUiModel(),
                lastDeletedIntakeEntry = null,
            )
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
        return HomeScreenViewModel(
            navigator,
            getIntakeEntriesUseCase,
            getCalorieGoalUseCase,
            saveIntakeEntryUseCase,
            deleteIntakeEntryUseCase,
            stringProvider,
        )
    }

}
