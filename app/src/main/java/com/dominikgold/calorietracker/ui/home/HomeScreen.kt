package com.dominikgold.calorietracker.ui.home

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.navigation.viewModel
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.ui.CalorieTrackerTopBar
import com.dominikgold.calorietracker.util.Translated

@Composable
fun HomeScreen() {
    val viewModel: HomeScreenViewModel = viewModel()
    Scaffold(topBar = { CalorieTrackerTopBar(title = Translated(R.string.home_screen_title)) }) {
        HomeScreenContent(onSetCalorieGoalClicked = viewModel::navigateToSetCalorieGoal)
    }
}

@Composable
fun HomeScreenContent(onSetCalorieGoalClicked: () -> Unit) {
    Stack(Modifier.fillMaxSize()) {
        Box(Modifier.padding(bottom = 32.dp).align(Alignment.BottomCenter)) {
            Button(onClick = onSetCalorieGoalClicked) {
                Text(text = Translated(resourceId = R.string.set_new_calorie_goal_button))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenContentPreview() {
    CalorieTrackerTheme {
        HomeScreenContent {}
    }
}
