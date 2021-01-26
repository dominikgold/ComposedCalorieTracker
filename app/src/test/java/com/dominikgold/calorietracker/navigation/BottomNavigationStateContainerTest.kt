package com.dominikgold.calorietracker.navigation

import com.dominikgold.calorietracker.di.DefaultViewModelProvider
import com.dominikgold.calorietracker.ui.bottomnav.BottomNavigationTab
import com.dominikgold.calorietracker.ui.bottomnav.BottomNavigationTab.HOME
import com.dominikgold.calorietracker.ui.bottomnav.BottomNavigationTab.SETTINGS
import com.dominikgold.calorietracker.ui.bottomnav.BottomNavigationTab.STATISTICS
import com.dominikgold.compose.viewmodel.ViewModelContainer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class BottomNavigationStateContainerTest {

    private fun createViewModelContainerMock() = mock<ViewModelContainer>()

    private lateinit var initialScreenForTabs: Map<BottomNavigationTab, NavigationStateEntry>

    private lateinit var stateContainer: BottomNavigationStateContainer

    @Before
    fun setUp() {
        initialScreenForTabs = mapOf(
            HOME to NavigationStateEntry(Screen.Home, mock()),
            STATISTICS to NavigationStateEntry(Screen.Statistics, mock()),
            SETTINGS to NavigationStateEntry(Screen.Settings, mock()),
        )
        stateContainer = BottomNavigationStateContainer(initialScreenForTabs)
    }

    @Test
    fun `initial screens for tabs are taken from given map`() {
        stateContainer.currentNavigationStateEntry.value.screen shouldBeEqualTo initialScreenForTabs[HOME]?.screen

        stateContainer.switchSelectedTab(STATISTICS)
        stateContainer.currentNavigationStateEntry.value.screen shouldBeEqualTo initialScreenForTabs[STATISTICS]?.screen

        stateContainer.switchSelectedTab(SETTINGS)
        stateContainer.currentNavigationStateEntry.value.screen shouldBeEqualTo initialScreenForTabs[SETTINGS]?.screen
    }

    @Test
    fun `adding a navigation state entry changes current navigation state entry`() {
        val viewModelContainer = createViewModelContainerMock()
        stateContainer.add(NavigationStateEntry(Screen.SetCalorieGoal, viewModelContainer))

        stateContainer.currentNavigationStateEntry.value shouldBeEqualTo NavigationStateEntry(Screen.SetCalorieGoal,
                                                                                              viewModelContainer)
    }

    @Test
    fun `switching selected tab changes current navigation state entry`() {
        stateContainer.switchSelectedTab(STATISTICS)

        stateContainer.currentNavigationStateEntry.value.screen shouldBeEqualTo Screen.Statistics
    }

    @Test
    fun `switching selected tab changes selected tab`() {
        stateContainer.switchSelectedTab(STATISTICS)

        stateContainer.selectedTab shouldBeEqualTo STATISTICS
    }

    @Test
    fun `switching selected tab does not clear view model container for current screen`() {
        stateContainer.switchSelectedTab(STATISTICS)

        verify(initialScreenForTabs.getValue(HOME).viewModelContainer, never()).release()
    }

    @Test
    fun `selecting tab that is in the history of selected tabs pushes it to top instead of adding to history`() {
        stateContainer.switchSelectedTab(STATISTICS)
        stateContainer.switchSelectedTab(SETTINGS)
        stateContainer.switchSelectedTab(STATISTICS)

        stateContainer.popBackStack()
        stateContainer.selectedTab shouldBeEqualTo SETTINGS
        stateContainer.popBackStack()
        stateContainer.selectedTab shouldBeEqualTo HOME
        stateContainer.popBackStack() shouldBe null
    }

    @Test
    fun `popping the back stack changes current screen to the previous screen on the current tab's back stack`() {
        stateContainer.add(NavigationStateEntry(Screen.SetCalorieGoal, createViewModelContainerMock()))

        stateContainer.popBackStack()

        stateContainer.currentNavigationStateEntry.value.screen shouldBeEqualTo Screen.Home
    }

    @Test
    fun `popping the last entry on a tab back stack switches to last tab in tab history`() {
        stateContainer.switchSelectedTab(STATISTICS)

        stateContainer.popBackStack()

        stateContainer.currentNavigationStateEntry.value.screen shouldBeEqualTo Screen.Home
        stateContainer.selectedTab shouldBeEqualTo HOME
    }

    @Test
    fun `popping the last entry on a tab back stack does not clear the view model container for that entry`() {
        stateContainer.switchSelectedTab(STATISTICS)

        stateContainer.popBackStack()

        verify(initialScreenForTabs.getValue(STATISTICS).viewModelContainer, never()).release()
    }

    @Test
    fun `popping the back stack without tab change releases the view model container`() {
        val viewModelContainer = createViewModelContainerMock()
        stateContainer.add(NavigationStateEntry(Screen.SetCalorieGoal, viewModelContainer))

        stateContainer.popBackStack()

        verify(viewModelContainer).release()
    }

    @Test
    fun `popping the last entry on the only tab in the tab history returns null`() {
        val result = stateContainer.popBackStack()

        result shouldBe null
    }

    @Test
    fun `clearing the selected tab's stack leaves only the initial screen in the history`() {
        val viewModelContainer1 = createViewModelContainerMock()
        stateContainer.add(NavigationStateEntry(Screen.SetCalorieGoal, viewModelContainer1))
        val viewModelContainer2 = createViewModelContainerMock()
        stateContainer.add(NavigationStateEntry(Screen.Settings, viewModelContainer2))

        stateContainer.clearSelectedTabStack()

        verify(viewModelContainer1).release()
        verify(viewModelContainer2).release()
        stateContainer.currentNavigationStateEntry.value shouldBeEqualTo initialScreenForTabs.getValue(HOME)
        stateContainer.popBackStack() shouldBe null
    }

    @Test
    fun `complex navigation flow`() {
        val viewModelContainer1 = createViewModelContainerMock()
        stateContainer.add(NavigationStateEntry(Screen.SetCalorieGoal, viewModelContainer1))

        stateContainer.switchSelectedTab(STATISTICS)
        val viewModelContainer2 = createViewModelContainerMock()
        stateContainer.add(NavigationStateEntry(Screen.SetCalorieGoal, viewModelContainer2))

        stateContainer.switchSelectedTab(SETTINGS)
        val viewModelContainer3 = createViewModelContainerMock()
        stateContainer.add(NavigationStateEntry(Screen.SetCalorieGoal, viewModelContainer3))

        stateContainer.switchSelectedTab(HOME)
        stateContainer.currentNavigationStateEntry.value shouldBeEqualTo NavigationStateEntry(Screen.SetCalorieGoal,
                                                                                              viewModelContainer1)

        stateContainer.popBackStack()
        stateContainer.currentNavigationStateEntry.value shouldBeEqualTo initialScreenForTabs.getValue(HOME)
        verify(viewModelContainer1).release()

        stateContainer.popBackStack()
        stateContainer.selectedTab shouldBeEqualTo SETTINGS
        stateContainer.currentNavigationStateEntry.value shouldBeEqualTo NavigationStateEntry(Screen.SetCalorieGoal,
                                                                                              viewModelContainer3)

        stateContainer.popBackStack()
        stateContainer.currentNavigationStateEntry.value shouldBeEqualTo initialScreenForTabs.getValue(SETTINGS)
        verify(viewModelContainer3).release()

        stateContainer.popBackStack()
        stateContainer.selectedTab shouldBeEqualTo STATISTICS
        stateContainer.currentNavigationStateEntry.value shouldBeEqualTo NavigationStateEntry(Screen.SetCalorieGoal,
                                                                                              viewModelContainer2)

        stateContainer.popBackStack()
        stateContainer.currentNavigationStateEntry.value shouldBeEqualTo initialScreenForTabs.getValue(STATISTICS)
        verify(viewModelContainer2).release()

        stateContainer.popBackStack() shouldBe null
    }

    @Test
    fun `complex save and restore state flow`() {
        stateContainer.add(NavigationStateEntry(Screen.SetCalorieGoal, createViewModelContainerMock()))
        stateContainer.switchSelectedTab(STATISTICS)

        val savedState = stateContainer.saveState()
        val newStateContainer = BottomNavigationStateContainer(initialScreenForTabs)
        newStateContainer.restoreState(savedState, DefaultViewModelProvider(mapOf()))

        stateContainer.selectedTab shouldBeEqualTo STATISTICS
        stateContainer.currentNavigationStateEntry.value.screen shouldBeEqualTo Screen.Statistics
        stateContainer.popBackStack()
        stateContainer.selectedTab shouldBeEqualTo HOME
        stateContainer.currentNavigationStateEntry.value.screen shouldBeEqualTo Screen.SetCalorieGoal
        stateContainer.popBackStack()
        stateContainer.currentNavigationStateEntry.value.screen shouldBeEqualTo Screen.Home
        stateContainer.popBackStack() shouldBe null
    }

}