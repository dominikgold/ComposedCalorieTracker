package com.dominikgold.calorietracker.ui.bottomnav

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.theming.primaryBlue
import com.dominikgold.calorietracker.util.InLightAndDarkTheme
import com.dominikgold.compose.viewmodel.viewModel

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
    BottomNavigation(backgroundColor = MaterialTheme.colors.surface) {
        availableTabs.forEach { tab ->
            val isSelected = tab == selectedTab
            val unselectedColor = MaterialTheme.colors.onSurface.copy(alpha = LocalContentAlpha.current)
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = null,
                        tint = if (isSelected) primaryBlue else unselectedColor,
                    )
                },
                label = { Text(tab.title, color = if (isSelected) primaryBlue else unselectedColor) },
                selected = isSelected,
                onClick = { onTabSelected(tab) },
            )
        }
    }
}

@Preview
@Composable
fun CalorieTrackerBottomNavigationPreview() {
    InLightAndDarkTheme {
        CalorieTrackerBottomNavigationContent(
            availableTabs = BottomNavigationTab.values(),
            selectedTab = BottomNavigationTab.HOME,
            onTabSelected = {},
        )
    }
}