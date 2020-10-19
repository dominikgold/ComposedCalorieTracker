package com.dominikgold.calorietracker.navigation

import com.dominikgold.calorietracker.di.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

interface Navigator {

    val currentScreen: StateFlow<Screen>

    val viewModelContainer: ViewModelContainer

    fun goBack(): Boolean

    fun switchScreen(to: Screen)

}

@Singleton
class DefaultNavigator @Inject constructor(private val viewModelProvider: ViewModelProvider) : Navigator {

    override val currentScreen = MutableStateFlow<Screen>(Screen.Home)

    override var viewModelContainer = ViewModelContainer(viewModelProvider)

    private val backStack = mutableListOf<BackStackEntry>()

    override fun goBack(): Boolean {
        if (backStack.isEmpty()) {
            return false
        }
        viewModelContainer.release()
        val backStackEntry = backStack.removeLast()
        currentScreen.value = backStackEntry.screen
        viewModelContainer = backStackEntry.viewModelContainer
        return true
    }

    override fun switchScreen(to: Screen) {
        val backStackEntry = BackStackEntry(currentScreen.value, viewModelContainer)
        currentScreen.value = to
        viewModelContainer = ViewModelContainer(viewModelProvider)
        backStack.add(backStackEntry)
    }

    private data class BackStackEntry(val screen: Screen, val viewModelContainer: ViewModelContainer)

}
