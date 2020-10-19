package com.dominikgold.calorietracker.ui.topbar

import com.dominikgold.calorietracker.ViewModel
import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.navigation.Navigator
import javax.inject.Inject

class TopBarViewModel(private val navigator: Navigator) : ViewModel() {

    fun onNavigateBackClicked() {
        navigator.goBack()
    }

}

class TopBarViewModelFactory @Inject constructor(
    private val navigator: Navigator,
) : ViewModelFactory<TopBarViewModel, Nothing, Nothing> {

    override fun create(savedState: Nothing?, parameters: Nothing?): TopBarViewModel {
        return TopBarViewModel(navigator)
    }

}