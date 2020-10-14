package com.dominikgold.calorietracker.ui.topbar

import com.dominikgold.calorietracker.ViewModel
import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.navigation.NavigationStateContainer
import javax.inject.Inject

class TopBarViewModel(private val navigationStateContainer: NavigationStateContainer) : ViewModel() {

    fun onNavigateBackClicked() {
        navigationStateContainer.goBack()
    }

}

class TopBarViewModelFactory @Inject constructor(
    private val navigationStateContainer: NavigationStateContainer,
) : ViewModelFactory<TopBarViewModel, Nothing, Nothing> {

    override fun create(savedState: Nothing?, parameters: Nothing?): TopBarViewModel {
        return TopBarViewModel(navigationStateContainer)
    }

}