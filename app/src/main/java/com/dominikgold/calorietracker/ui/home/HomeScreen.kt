package com.dominikgold.calorietracker.ui.home

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.navigation.viewModel
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.ui.topbar.CalorieTrackerTopBar
import com.dominikgold.calorietracker.util.Translated

@Composable
fun HomeScreen() {
    val viewModel: HomeScreenViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState()
    HomeScreenContent(onSetCalorieGoalClicked = viewModel::navigateToSetCalorieGoal, uiState = uiState.value)
}

@Composable
private fun HomeScreenContent(uiState: HomeScreenUiModel, onSetCalorieGoalClicked: () -> Unit) {
    Scaffold(topBar = { CalorieTrackerTopBar(title = Translated(R.string.home_screen_title)) }) {
        Box(Modifier.fillMaxSize().padding(16.dp)) {
            if (uiState.showNoCalorieGoalSet) {
                NoCalorieGoalSet()
            } else if (uiState.calorieGoal != null) {
                HomeScreenCalorieGoal(uiModel = uiState.calorieGoal)
                LazyColumnFor(items = uiState.intakeEntries) {

                }
            }
            SetNewCalorieGoalButton(onSetCalorieGoalClicked)
        }
    }
}

@Composable
private fun BoxScope.SetNewCalorieGoalButton(onSetCalorieGoalClicked: () -> Unit) {
    Card(Modifier.padding(bottom = 32.dp).align(Alignment.BottomCenter), elevation = 8.dp) {
        Button(onClick = onSetCalorieGoalClicked) {
            Text(text = Translated(resourceId = R.string.set_new_calorie_goal_button))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenContentPreview() {
    CalorieTrackerTheme {
        HomeScreenContent(HomeScreenUiModel(true, null, listOf())) {}
    }
}
