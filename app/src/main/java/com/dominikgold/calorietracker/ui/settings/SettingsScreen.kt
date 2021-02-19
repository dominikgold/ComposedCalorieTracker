package com.dominikgold.calorietracker.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.theming.TextStyles
import com.dominikgold.calorietracker.ui.bottomnav.CalorieTrackerBottomNavigation
import com.dominikgold.calorietracker.ui.topbar.CalorieTrackerTopBar
import com.dominikgold.calorietracker.util.translated
import com.dominikgold.calorietracker.util.inLightAndDarkTheme
import com.dominikgold.compose.viewmodel.viewModel

@Composable
fun SettingsScreen() {
    Scaffold(
        topBar = { CalorieTrackerTopBar(title = translated(R.string.settings_screen_title)) },
        bottomBar = { CalorieTrackerBottomNavigation() },
    ) {
        val viewModel: SettingsViewModel = viewModel()
        SettingsScreenContent(viewModel::onSettingsItemClicked)
    }
}

@Composable
private fun SettingsScreenContent(onItemClicked: (SettingsListItem) -> Unit) {
    LazyColumn(Modifier
                   .padding(16.dp)
                   .fillMaxSize()) {
        item {
            SettingsItem(
                onClick = { onItemClicked(SettingsListItem.ChangeCalorieGoal) },
                settingsListItem = SettingsListItem.ChangeCalorieGoal,
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SettingsItem(
                onClick = { onItemClicked(SettingsListItem.ChangeMeasurementSystem) },
                settingsListItem = SettingsListItem.ChangeMeasurementSystem,
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SettingsItem(
                onClick = { onItemClicked(SettingsListItem.SetReminders) },
                settingsListItem = SettingsListItem.SetReminders,
            )
        }
    }
}

@Composable
private fun SettingsItem(onClick: () -> Unit, settingsListItem: SettingsListItem) {
    Row(Modifier.clickable(onClick = onClick), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = settingsListItem.icon, contentDescription = null, modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = settingsListItem.title, style = TextStyles.Title)
    }
}

@Preview
@Composable
fun SettingsScreenContentPreview() {
    inLightAndDarkTheme {
        SettingsScreenContent({})
    }
}

@Preview
@Composable
fun SettingsItemPreview() {
    inLightAndDarkTheme {
        SettingsItem(onClick = { }, settingsListItem = SettingsListItem.ChangeCalorieGoal)
    }
}

