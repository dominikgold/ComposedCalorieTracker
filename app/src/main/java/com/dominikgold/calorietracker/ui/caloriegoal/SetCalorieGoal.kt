package com.dominikgold.calorietracker.ui.caloriegoal

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.entities.MacroSplit
import com.dominikgold.calorietracker.navigation.viewModel
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.ui.topbar.CalorieTrackerTopBar
import com.dominikgold.calorietracker.ui.topbar.TopBarActionTextButton
import com.dominikgold.calorietracker.util.LengthInputFilter
import com.dominikgold.calorietracker.util.Translated

@Composable
fun SetCalorieGoalScreen() {
    val viewModel: SetCalorieGoalViewModel = viewModel()
    val uiModelState = viewModel.uiModelState.collectAsState()
    SetCalorieGoalContent(
        uiModel = uiModelState.value,
        onTdeeChanged = viewModel::updateTdee,
        onMacroSplitChosen = viewModel::updateMacroSplit,
        onSaveButtonClicked = viewModel::saveCalorieGoal,
    )
}

@Composable
private fun SetCalorieGoalContent(
    uiModel: SetCalorieGoalUiModel,
    onTdeeChanged: (Int?) -> Unit,
    onMacroSplitChosen: (MacroSplit) -> Unit,
    onSaveButtonClicked: () -> Unit,
) {
    Scaffold(topBar = {
        CalorieTrackerTopBar(
            title = Translated(R.string.set_calorie_goal_title),
            navigationIcon = vectorResource(id = R.drawable.vec_icon_close),
            actions = {
                TopBarActionTextButton(
                    text = Translated(R.string.top_bar_action_save),
                    enabled = uiModel.isSaveButtonEnabled,
                    onClick = onSaveButtonClicked,
                )
            },
        )
    }) {
        SetCalorieGoalBody(uiModel, onTdeeChanged, onMacroSplitChosen)
    }
}

@Composable
private fun SetCalorieGoalBody(
    uiModel: SetCalorieGoalUiModel,
    onTdeeChanged: (Int?) -> Unit,
    onMacroSplitChosen: (MacroSplit) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        TextField(
            value = uiModel.tdeeInput?.toString() ?: "",
            placeholder = {
                Text(text = Translated(resourceId = R.string.tdee_input_placeholder))
            },
            onValueChange = LengthInputFilter(maxLength = 5) { newInput ->
                onTdeeChanged(newInput.toIntOrNull())
            },
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        ChooseMacroSplit(uiModel.macroSplitUiModel, onMacroSplitChosen)
    }
}

@Composable
@Preview(showBackground = true)
fun SetCalorieGoalContentPreview() {
    CalorieTrackerTheme {
        SetCalorieGoalContent(SetCalorieGoalUiModel(null, null, false), {}, {}, {})
    }
}
