package com.dominikgold.calorietracker.ui.caloriegoal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.entities.Grams
import com.dominikgold.calorietracker.entities.MacroSplit
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.ui.topbar.CalorieTrackerTopBar
import com.dominikgold.calorietracker.ui.topbar.TopBarActionTextButton
import com.dominikgold.calorietracker.util.LengthInputFilter
import com.dominikgold.calorietracker.util.NaturalNumberInputFilter
import com.dominikgold.calorietracker.util.translated
import com.dominikgold.calorietracker.util.inputFilters
import com.dominikgold.compose.viewmodel.viewModel

@Composable
fun SetCalorieGoalScreen() {
    val savedState: MutableState<SetCalorieGoalUiState?> = savedInstanceState { null }
    val viewModel: SetCalorieGoalViewModel = viewModel(savedState = savedState.value)
    val uiState by viewModel.uiState.collectAsState()
    savedState.value = uiState
    SetCalorieGoalContent(
        uiState = uiState,
        setCalorieGoalActions = viewModel,
        onSaveButtonClicked = viewModel::saveCalorieGoal,
    )
}

@Composable
private fun SetCalorieGoalContent(
    uiState: SetCalorieGoalUiState,
    setCalorieGoalActions: SetCalorieGoalActions,
    onSaveButtonClicked: () -> Unit,
) {
    Scaffold(topBar = {
        CalorieTrackerTopBar(
            title = translated(R.string.set_calorie_goal_title),
            navigationIcon = vectorResource(id = R.drawable.vec_icon_close),
            actions = {
                TopBarActionTextButton(
                    text = translated(R.string.top_bar_action_save),
                    enabled = uiState.isSaveButtonEnabled,
                    onClick = onSaveButtonClicked,
                )
            },
        )
    }) {
        SetCalorieGoalBody(uiState, setCalorieGoalActions)
    }
}

@Composable
private fun SetCalorieGoalBody(uiState: SetCalorieGoalUiState, setCalorieGoalActions: SetCalorieGoalActions) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        OutlinedTextField(
            value = uiState.tdeeInput?.toString() ?: "",
            placeholder = {
                Text(text = translated(resourceId = R.string.tdee_input_placeholder))
            },
            onValueChange = inputFilters(NaturalNumberInputFilter(), LengthInputFilter(maxLength = 5)) { newInput ->
                setCalorieGoalActions.updateTdee(newInput.toIntOrNull())
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        SetMacroGoals(uiState, setCalorieGoalActions)
    }
}

@Composable
@Preview(showBackground = true)
fun SetCalorieGoalContentPreview() {
    CalorieTrackerTheme {
        SetCalorieGoalContent(SetCalorieGoalUiState(null, null, null, null, null), object : SetCalorieGoalActions {
            override fun updateTdee(tdee: Int?) {}
            override fun updateChosenMacroSplit(macroSplit: MacroSplit?) {}
            override fun updateProtein(protein: Grams?) {}
            override fun updateCarbohydrates(carbohydrates: Grams?) {}
            override fun updateFat(fat: Grams?) {}
        }) {}
    }
}
