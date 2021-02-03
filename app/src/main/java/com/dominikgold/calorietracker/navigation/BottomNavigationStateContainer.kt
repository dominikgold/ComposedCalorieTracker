package com.dominikgold.calorietracker.navigation

import android.os.Parcelable
import com.dominikgold.calorietracker.di.DefaultViewModelProvider
import com.dominikgold.calorietracker.ui.bottomnav.BottomNavigationTab
import com.dominikgold.compose.viewmodel.DefaultViewModelContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

/**
 * This class manages the navigation state for the bottom navigation.
 * Each entry in the given [initialScreenForTabs] will be associated with its own back stack - meaning that every tab in
 * the bottom navigation will have its own back stack. Calling [popBackStack] will first go back in the history of
 * screens of the currently selected tab and when there is no screen in that history left, switch to the previously
 * selected tab.
 * Each tab can only appear once in the history of selected tabs. This means that if a tab is already in the history, it
 * will simply be pushed to the top of the stack when it is selected again.
 * Example: Tab 1 -> Tab 2 -> Tab 3 -> Tab 2 will result in following back navigation: Tab 2 -> Tab 3 -> Tab 1.
 * @param initialScreenForTabs the keys of this map should represent all tabs available in the bottom navigation,
 *                             associated with their initial screens.
 */
class BottomNavigationStateContainer(initialScreenForTabs: Map<BottomNavigationTab, NavigationStateEntry>) {

    private val selectedTabHistory = mutableListOf(BottomNavigationTab.HOME)
    private val tabScreenHistories: Map<BottomNavigationTab, MutableList<NavigationStateEntry>> =
        initialScreenForTabs.mapValues { mutableListOf(it.value) }

    val selectedTab: BottomNavigationTab
        get() = selectedTabHistory.last()

    private val _currentNavigationStateEntry = MutableStateFlow(tabScreenHistories.getValue(selectedTab).last())
    val currentNavigationStateEntry: StateFlow<NavigationStateEntry>
        get() = _currentNavigationStateEntry

    fun add(navigationStateEntry: NavigationStateEntry) {
        tabScreenHistories.getValue(selectedTab).add(navigationStateEntry)
        updateCurrentNavigationStateEntry()
    }

    fun switchSelectedTab(tab: BottomNavigationTab) {
        if (tab in selectedTabHistory) {
            selectedTabHistory.remove(tab)
        }
        selectedTabHistory.add(tab)
        updateCurrentNavigationStateEntry()
    }

    fun clearSelectedTabStack() {
        val currentTabHistory = tabScreenHistories.getValue(selectedTab)
        if (currentTabHistory.size == 1) {
            return
        }
        val iterator = currentTabHistory.iterator()
        // skip the initial entry in the list
        iterator.next()
        while (iterator.hasNext()) {
            val poppedEntry = iterator.next()
            poppedEntry.viewModelContainer.release()
            iterator.remove()
        }
        updateCurrentNavigationStateEntry()
    }

    /**
     * Triggers a back navigation in the navigation state of the bottom navigation.
     * @return The popped [NavigationStateEntry] or null if there is no screen to go back to
     */
    fun popBackStack(): NavigationStateEntry? {
        val tabScreenHistory = tabScreenHistories.getValue(selectedTab)
        // when we are on the last screen remaining in the tab screen history, we go back in the selected tab history
        if (tabScreenHistory.size == 1) {
            // when we are on the last tab in the selected tab history, we do nothing and return null
            if (selectedTabHistory.size == 1) {
                return null
            } else {
                val poppedTab = selectedTabHistory.removeLast()
                updateCurrentNavigationStateEntry()
                return tabScreenHistories.getValue(poppedTab).last()
            }
        }
        val poppedEntry = tabScreenHistories.getValue(selectedTab).removeLast()
        poppedEntry.viewModelContainer.release()
        updateCurrentNavigationStateEntry()
        return poppedEntry
    }

    private fun updateCurrentNavigationStateEntry() {
        _currentNavigationStateEntry.value = tabScreenHistories.getValue(selectedTab).last()
    }

    fun saveState() = BottomNavigationSavedState(
        tabScreenHistories = tabScreenHistories.mapValues { (_, history) ->
            history.map { it.screen }
        },
        selectedTabHistory = selectedTabHistory.toList(),
    )

    fun restoreState(state: BottomNavigationSavedState, viewModelProvider: DefaultViewModelProvider) {
        selectedTabHistory.clear()
        selectedTabHistory.addAll(state.selectedTabHistory)
        state.tabScreenHistories.forEach { (tab, history) ->
            this.tabScreenHistories.getValue(tab).clear()
            this.tabScreenHistories.getValue(tab).addAll(history.map { screen ->
                NavigationStateEntry(screen, DefaultViewModelContainer(viewModelProvider))
            })
        }
    }

}

@Parcelize
data class BottomNavigationSavedState(
    val tabScreenHistories: Map<BottomNavigationTab, List<Screen>>,
    val selectedTabHistory: List<BottomNavigationTab>,
) : Parcelable
