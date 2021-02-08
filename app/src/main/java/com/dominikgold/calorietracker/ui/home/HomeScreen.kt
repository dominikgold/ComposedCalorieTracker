package com.dominikgold.calorietracker.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.material.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.compose.viewmodel.viewModel
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.theming.TextStyles
import com.dominikgold.calorietracker.theming.components.StandardTextButton
import com.dominikgold.calorietracker.ui.bottomnav.BottomNavigationTab
import com.dominikgold.calorietracker.ui.bottomnav.CalorieTrackerBottomNavigation
import com.dominikgold.calorietracker.ui.topbar.CalorieTrackerTopBar
import com.dominikgold.calorietracker.ui.topbar.TopBarActionTextButton
import com.dominikgold.calorietracker.util.Translated

@Composable
fun HomeScreen() {
    val viewModel: HomeScreenViewModel = viewModel()
    viewModel.reload()
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            CalorieTrackerTopBar(title = Translated(R.string.home_screen_title))
        },
        bottomBar = { CalorieTrackerBottomNavigation() },
    ) {
        HomeScreenContent(
            onIntakeEntryAdded = viewModel::addIntakeEntry,
            uiState = uiState.value,
            greeting = viewModel.greeting,
            onSetCalorieGoalClicked = viewModel::navigateToSetCalorieGoal,
        )
    }
}

@Composable
private fun HomeScreenContent(
    uiState: HomeScreenUiModel,
    greeting: String,
    onIntakeEntryAdded: (AddIntakeEntryUiModel) -> Unit,
    onSetCalorieGoalClicked: () -> Unit,
) {
    Box(Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)) {
        if (uiState.showNoCalorieGoalSet) {
            NoCalorieGoalSet(onSetCalorieGoalClicked = onSetCalorieGoalClicked)
        } else if (uiState.calorieGoal != null) {
            val isAddIntakeEntrySectionVisible = remember { mutableStateOf(false) }
            LazyColumn {
                item {
                    Column(Modifier.padding(horizontal = 16.dp)) {
                        HomeScreenGreeting(greeting = greeting)
                        Spacer(modifier = Modifier.height(24.dp))
                        HomeScreenCalorieGoal(uiModel = uiState.calorieGoal)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                items(items = uiState.intakeEntries) {
                    Spacer(modifier = Modifier.height(8.dp))
                    IntakeEntryCard(uiModel = it)
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
                item {
                    AnimatedAddIntakeEntryButton(
                        isVisible = !isAddIntakeEntrySectionVisible.value,
                        enterAnimationDelay = 400,
                        onClick = { isAddIntakeEntrySectionVisible.value = true },
                    )
                }
                item {
                    AnimatedAddIntakeEntry(
                        isVisible = isAddIntakeEntrySectionVisible.value,
                        enterAnimationDelay = 200,
                        onConfirmed = {
                            isAddIntakeEntrySectionVisible.value = false
                            onIntakeEntryAdded(it)
                        },
                        onCancelled = { isAddIntakeEntrySectionVisible.value = false },
                    )
                }
                item { Spacer(modifier = Modifier.height(64.dp)) }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedAddIntakeEntryButton(
    isVisible: Boolean,
    enterAnimationDelay: Int,
    onClick: () -> Unit,
) {
    val expandVertically = expandVertically(
        expandFrom = Alignment.Top,
        animSpec = TweenSpec(durationMillis = 200, delay = enterAnimationDelay),
    )
    val fadeIn = fadeIn(animSpec = TweenSpec(durationMillis = 200, delay = enterAnimationDelay))
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn + expandVertically,
        exit = fadeOut(animSpec = TweenSpec(durationMillis = 200)),
    ) {
        Box(Modifier.padding(horizontal = 16.dp)) {
            StandardTextButton(text = Translated(R.string.add_intake_entry_button), onClick = onClick)
        }
    }
}

@Composable
fun HomeScreenGreeting(greeting: String) {
    Text(text = greeting, style = TextStyles.Headline)
}

@Composable
@Preview
fun HomeScreenContentPreview() {
    CalorieTrackerTheme(darkTheme = true) {
        HomeScreenContent(
            HomeScreenUiModel(
                showNoCalorieGoalSet = false,
                calorieGoal = CalorieGoalUiModel(
                    totalCalories = 2000,
                    caloriesLeft = 500,
                    carbohydratesGoal = MacroGoalUiModel(200, 50),
                    proteinGoal = MacroGoalUiModel(100, 20),
                    fatGoal = MacroGoalUiModel(50, 10),
                ),
                intakeEntries = listOf(IntakeEntryUiModel("essen", 1500, 150, 80, 40)),
            ),
            "Good morning!",
            {},
            {},
        )
    }
}
