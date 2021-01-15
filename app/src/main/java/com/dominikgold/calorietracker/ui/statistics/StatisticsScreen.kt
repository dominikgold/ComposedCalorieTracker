package com.dominikgold.calorietracker.ui.statistics

import androidx.compose.material.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.ui.bottomnav.CalorieTrackerBottomNavigation
import com.dominikgold.calorietracker.ui.topbar.CalorieTrackerTopBar
import com.dominikgold.calorietracker.util.Translated

@Composable
fun StatisticsScreen() {
    Scaffold(
        topBar = { CalorieTrackerTopBar(title = Translated(R.string.statistics_screen_title)) },
        bottomBar = { CalorieTrackerBottomNavigation() },
    ) {
        StatisticsScreenContent()
    }
}

@Composable
private fun StatisticsScreenContent() {
    Box(Modifier.fillMaxSize()) {
        Text(text = "Coming soon", modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
fun StatisticsScreenContentPreview() {
    StatisticsScreenContent()
}