package com.dominikgold.calorietracker.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.theming.TextStyles
import com.dominikgold.calorietracker.theming.components.StandardTextButton
import com.dominikgold.calorietracker.ui.bottomnav.CalorieTrackerBottomNavigation
import com.dominikgold.calorietracker.ui.topbar.CalorieTrackerTopBar
import com.dominikgold.calorietracker.util.translated
import com.dominikgold.compose.viewmodel.viewModel

@Composable
fun HomeScreen() {
    val viewModel: HomeScreenViewModel = viewModel()
    viewModel.reload()
    HomeScreenScaffold(viewModel = viewModel)
}

@Composable
private fun HomeScreenScaffold(viewModel: HomeScreenViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    if (uiState.lastDeletedIntakeEntry != null) {
        UndoDeletionSnackbar(
            deletedIntakeEntry = uiState.lastDeletedIntakeEntry!!,
            scaffoldState = scaffoldState,
            onDismissed = viewModel::resetLastDeletedIntakeEntry,
            onUndoClicked = viewModel::undoIntakeEntryDeletion,
        )
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CalorieTrackerTopBar(title = translated(R.string.home_screen_title))
        },
        bottomBar = { CalorieTrackerBottomNavigation() },
    ) {
        HomeScreenContent(
            uiState = uiState,
            greeting = viewModel.greeting,
            onIntakeEntryAdded = viewModel::addIntakeEntry,
            onIntakeEntryDeleted = viewModel::deleteIntakeEntry,
            onSetCalorieGoalClicked = viewModel::navigateToSetCalorieGoal,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeScreenUiModel,
    greeting: String,
    onIntakeEntryAdded: (AddIntakeEntryUiModel) -> Unit,
    onIntakeEntryDeleted: (IntakeEntryUiModel) -> Unit,
    onSetCalorieGoalClicked: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        if (uiState.showNoCalorieGoalSet) {
            NoCalorieGoalSet(onSetCalorieGoalClicked = onSetCalorieGoalClicked)
        } else if (uiState.calorieGoal != null) {
            val isAddIntakeEntrySectionVisible = remember { mutableStateOf(false) }
            LazyColumn(contentPadding = PaddingValues(vertical = 16.dp, horizontal = 0.dp)) {
                item {
                    Column(Modifier.padding(horizontal = 16.dp)) {
                        HomeScreenGreeting(greeting = greeting)
                        Spacer(modifier = Modifier.height(24.dp))
                        HomeScreenCalorieGoal(uiModel = uiState.calorieGoal)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                with(uiState) {
                    val intakeEntriesToDisplay = if (previousIntakeEntries.size > currentIntakeEntries.size) {
                        previousIntakeEntries
                    } else {
                        currentIntakeEntries
                    }
                    var animationDelay = 0
                    items(items = intakeEntriesToDisplay) { intakeEntry ->
                        Spacer(modifier = Modifier.height(8.dp))
                        val animationState = intakeEntry.animationState(currentIntakeEntries, previousIntakeEntries)
                        if (animationState != null) {
                            AnimatedIntakeEntryCard(
                                uiModel = intakeEntry,
                                animationState = animationState,
                                animationDelay = animationDelay,
                                onIntakeEntryDeleted = onIntakeEntryDeleted,
                            )
                            animationDelay += 200
                        } else {
                            IntakeEntryCard(uiModel = intakeEntry, onIntakeEntryDeleted = onIntakeEntryDeleted)
                        }
                    }
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
            StandardTextButton(text = translated(R.string.add_intake_entry_button), onClick = onClick)
        }
    }
}

@Composable
fun HomeScreenGreeting(greeting: String) {
    Text(text = greeting, style = TextStyles.Headline)
}

@Composable
private fun UndoDeletionSnackbar(
    deletedIntakeEntry: IntakeEntryUiModel,
    scaffoldState: ScaffoldState,
    onDismissed: () -> Unit,
    onUndoClicked: () -> Unit,
) {
    val snackbarMessage = translated(R.string.intake_entry_deleted, listOf(deletedIntakeEntry.name))
    val undoButtonText = translated(R.string.undo_deletion_button)
    LaunchedEffect(key1 = deletedIntakeEntry) {
        val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
            message = snackbarMessage,
            actionLabel = undoButtonText,
            duration = SnackbarDuration.Long,
        )
        when (snackbarResult) {
            SnackbarResult.Dismissed -> onDismissed()
            SnackbarResult.ActionPerformed -> onUndoClicked()
        }
    }
}

@Composable
@Preview
fun HomeScreenContentPreview() {
    CalorieTrackerTheme(darkTheme = true) {
        HomeScreenContent(uiState = HomeScreenUiModel(
            showNoCalorieGoalSet = false,
            calorieGoal = CalorieGoalUiModel(
                totalCalories = 2000,
                caloriesLeft = 500,
                carbohydratesGoal = MacroGoalUiModel(200, 50),
                proteinGoal = MacroGoalUiModel(100, 20),
                fatGoal = MacroGoalUiModel(50, 10),
            ),
            currentIntakeEntries = listOf(IntakeEntryUiModel("", "essen", 1500, 150, 80, 40)),
        ), greeting = "Good morning!", {}, {}, {})
    }
}
