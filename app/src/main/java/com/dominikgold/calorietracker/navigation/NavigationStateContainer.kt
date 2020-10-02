package com.dominikgold.calorietracker.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

interface NavigationStateContainer {

    val currentScreen: StateFlow<Screen>

    val viewModelContainer: ViewModelContainer

    fun switchScreen(to: Screen)

}

@Singleton
class DefaultNavigationStateContainer @Inject constructor(override val viewModelContainer: ViewModelContainer) : NavigationStateContainer {

    override val currentScreen = MutableStateFlow<Screen>(Screen.Home)

    override fun switchScreen(to: Screen) {
        viewModelContainer.release()
        currentScreen.value = to
    }

}
