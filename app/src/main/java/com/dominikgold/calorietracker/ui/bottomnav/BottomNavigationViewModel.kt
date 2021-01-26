package com.dominikgold.calorietracker.ui.bottomnav

import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.navigation.Navigator
import com.dominikgold.compose.viewmodel.ViewModel
import javax.inject.Inject

class BottomNavigationViewModel(private val navigator: Navigator) : ViewModel() {

    val availableTabs = BottomNavigationTab.values()

    val selectedTab: BottomNavigationTab
        get() = navigator.selectedBottomNavigationTab

    fun onTabSelected(tab: BottomNavigationTab) {
        navigator.onBottomNavigationTabSelected(tab)
    }

}

class BottomNavigationViewModelFactory @Inject constructor(private val navigator: Navigator) :
    ViewModelFactory<BottomNavigationViewModel, Nothing, Nothing> {

    override fun create(savedState: Nothing?, parameters: Nothing?): BottomNavigationViewModel {
        return BottomNavigationViewModel(navigator)
    }

}