package com.dominikgold.calorietracker.ui.settings

import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.navigation.Navigator
import com.dominikgold.calorietracker.navigation.Screen
import com.dominikgold.compose.viewmodel.ViewModel
import javax.inject.Inject

class SettingsViewModel(private val navigator: Navigator) : ViewModel() {

    fun onSettingsItemClicked(item: SettingsListItem) {
        when (item) {
            SettingsListItem.ChangeCalorieGoal -> navigator.switchScreen(Screen.SetCalorieGoal)
            SettingsListItem.ChangeMeasurementSystem -> TODO()
            SettingsListItem.SetReminders -> TODO()
        }
    }

}

class SettingsViewModelFactory @Inject constructor(private val navigator: Navigator) :
    ViewModelFactory<SettingsViewModel, Nothing, Nothing> {

    override fun create(savedState: Nothing?, parameters: Nothing?) = SettingsViewModel(navigator)

}
