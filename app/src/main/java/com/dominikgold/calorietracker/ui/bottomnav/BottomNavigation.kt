package com.dominikgold.calorietracker.ui.bottomnav

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.navigation.viewModel
import com.dominikgold.calorietracker.util.inLightAndDarkTheme

@Composable
fun CalorieTrackerBottomNavigation() {
    val viewModel = viewModel<BottomNavigationViewModel>()
    CalorieTrackerBottomNavigationContent(
        availableTabs = viewModel.availableTabs,
        selectedTab = viewModel.selectedTab,
        onTabSelected = viewModel::onTabSelected,
    )
}

@Composable
fun CalorieTrackerBottomNavigationContent(
    availableTabs: Array<BottomNavigationTab>,
    selectedTab: BottomNavigationTab,
    onTabSelected: (BottomNavigationTab) -> Unit,
) {
    BottomNavigation {
        availableTabs.forEach { tab ->
            BottomNavigationItem(
                icon = { Icon(asset = tab.icon) },
                label = { Text(tab.title) },
                selected = tab == selectedTab,
                onClick = { onTabSelected(tab) },
            )
        }
    }
}

@Preview
@Composable
fun CalorieTrackerBottomNavigationPreview() {
    inLightAndDarkTheme {
        CalorieTrackerBottomNavigationContent(
            availableTabs = BottomNavigationTab.values(),
            selectedTab = BottomNavigationTab.HOME,
            onTabSelected = {},
        )
    }
}