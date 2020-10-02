package com.dominikgold.calorietracker.ui.home

import com.dominikgold.calorietracker.ViewModel
import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.navigation.NavigationStateContainer
import com.dominikgold.calorietracker.navigation.Screen
import javax.inject.Inject

class HomeScreenViewModel(private val navigator: NavigationStateContainer) : ViewModel() {

    fun navigateToSetCalorieGoal() {
        navigator.switchScreen(Screen.SetCalorieGoal)
    }

}

class HomeScreenViewModelFactory @Inject constructor(private val navigator: NavigationStateContainer) :
    ViewModelFactory<HomeScreenViewModel, Nothing, Nothing> {

    override fun create(savedState: Nothing?, parameters: Nothing?): HomeScreenViewModel {
        return HomeScreenViewModel(navigator)
    }

}
