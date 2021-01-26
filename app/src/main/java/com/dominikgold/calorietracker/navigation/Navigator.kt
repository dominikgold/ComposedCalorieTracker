package com.dominikgold.calorietracker.navigation

import com.dominikgold.calorietracker.di.DefaultViewModelProvider
import com.dominikgold.calorietracker.ui.bottomnav.BottomNavigationTab
import com.dominikgold.calorietracker.ui.bottomnav.BottomNavigationTab.*
import com.dominikgold.compose.viewmodel.DefaultViewModelContainer
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

interface Navigator {

    val currentNavigationStateEntry: StateFlow<NavigationStateEntry>

    val selectedBottomNavigationTab: BottomNavigationTab

    fun goBack(): Boolean

    fun switchScreen(to: Screen)

    fun onBottomNavigationTabSelected(tab: BottomNavigationTab)

    fun saveState(): NavigatorState

    fun restoreState(state: NavigatorState)

}

@Singleton
class DefaultNavigator @Inject constructor(private val viewModelProvider: DefaultViewModelProvider) : Navigator {

    private val bottomNavigationStateContainer = BottomNavigationStateContainer(initialScreenForTabs = mapOf(
        HOME to NavigationStateEntry(Screen.Home, DefaultViewModelContainer(viewModelProvider)),
        STATISTICS to NavigationStateEntry(Screen.Statistics, DefaultViewModelContainer(viewModelProvider)),
        SETTINGS to NavigationStateEntry(Screen.Settings, DefaultViewModelContainer(viewModelProvider)),
    ))

    override val currentNavigationStateEntry: StateFlow<NavigationStateEntry>
        get() = bottomNavigationStateContainer.currentNavigationStateEntry

    override val selectedBottomNavigationTab: BottomNavigationTab
        get() = bottomNavigationStateContainer.selectedTab

    override fun goBack(): Boolean {
        return bottomNavigationStateContainer.popBackStack() != null
    }

    override fun switchScreen(to: Screen) {
        bottomNavigationStateContainer.add(NavigationStateEntry(
            screen = to,
            viewModelContainer = DefaultViewModelContainer(viewModelProvider),
        ))
    }

    override fun onBottomNavigationTabSelected(tab: BottomNavigationTab) {
        if (bottomNavigationStateContainer.selectedTab == tab) {
            bottomNavigationStateContainer.clearSelectedTabStack()
        } else {
            bottomNavigationStateContainer.switchSelectedTab(tab)
        }
    }

    override fun saveState() = NavigatorState(bottomNavigationStateContainer.saveState())

    override fun restoreState(state: NavigatorState) {
        bottomNavigationStateContainer.restoreState(state.bottomNavigationSavedState, viewModelProvider)
    }

}
