package com.dominikgold.calorietracker.navigation

import android.os.Parcelable
import androidx.compose.foundation.currentTextStyle
import com.dominikgold.calorietracker.di.ViewModelProvider
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

interface Navigator {

    val currentScreen: StateFlow<Screen>

    val viewModelContainer: ViewModelContainer

    fun goBack(): Boolean

    fun switchScreen(to: Screen)

    fun saveState(): NavigatorState

    fun restoreState(state: NavigatorState)

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
        viewModelContainer = backStackEntry.viewModelContainer
        currentScreen.value = backStackEntry.screen
        return true
    }

    override fun switchScreen(to: Screen) {
        val backStackEntry = BackStackEntry(currentScreen.value, viewModelContainer)
        viewModelContainer = ViewModelContainer(viewModelProvider)
        currentScreen.value = to
        backStack.add(backStackEntry)
    }

    override fun saveState() = NavigatorState(backStack.map { it.screen }, currentScreen.value)

    override fun restoreState(state: NavigatorState) {
        backStack.addAll(state.backStack.map { BackStackEntry(it, ViewModelContainer(viewModelProvider)) })
        currentScreen.value = state.currentScreen
    }

    private data class BackStackEntry(val screen: Screen, val viewModelContainer: ViewModelContainer)

}
