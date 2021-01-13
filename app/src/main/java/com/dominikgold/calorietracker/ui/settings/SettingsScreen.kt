package com.dominikgold.calorietracker.ui.settings

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.ui.bottomnav.CalorieTrackerBottomNavigation
import com.dominikgold.calorietracker.ui.topbar.CalorieTrackerTopBar
import com.dominikgold.calorietracker.util.Translated

@Composable
fun SettingsScreen() {
    Scaffold(
        topBar = { CalorieTrackerTopBar(title = Translated(R.string.settings_screen_title)) },
        bottomBar = { CalorieTrackerBottomNavigation() },
    ) {
        SettingsScreenContent()
    }
}

@Composable
private fun SettingsScreenContent() {
    Box(Modifier.fillMaxSize()) {
        Text(text = "Coming soon", modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
fun SettingsScreenContentPreview() {
    SettingsScreenContent()
}