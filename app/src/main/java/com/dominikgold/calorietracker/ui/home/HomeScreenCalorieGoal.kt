package com.dominikgold.calorietracker.ui.home

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.util.Translated

@Composable
fun NoCalorieGoalSet() {
    Text(
        text = Translated(R.string.home_screen_no_calorie_goal_set_message),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(32.dp),
    )
}

@Composable
fun HomeScreenCalorieGoal(uiModel: CalorieGoalUiModel) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = Translated(R.string.home_screen_calories_left, listOf(uiModel.caloriesLeft, uiModel.totalCalories)),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(Modifier.fillMaxWidth()) {
            MacroAmountLeft(
                title = Translated(R.string.home_screen_carbs_left),
                macrosLeft = uiModel.carbohydratesLeft,
                totalMacros = uiModel.totalCarbohydrates,
            )
            MacroAmountLeft(
                title = Translated(R.string.home_screen_protein_left),
                macrosLeft = uiModel.proteinLeft,
                totalMacros = uiModel.totalProtein,
            )
            MacroAmountLeft(
                title = Translated(R.string.home_screen_fat_left),
                macrosLeft = uiModel.fatLeft,
                totalMacros = uiModel.totalFat,
            )
        }
    }
}

@Composable
private fun RowScope.MacroAmountLeft(title: String, macrosLeft: Int, totalMacros: Int) {
    Column(Modifier.weight(1f)) {
        Text(text = title, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = Translated(R.string.home_screen_macros_left_format, listOf(macrosLeft, totalMacros)),
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenCalorieGoalPreview() {
    CalorieTrackerTheme {
        Box(Modifier.fillMaxWidth()) {
            HomeScreenCalorieGoal(uiModel = CalorieGoalUiModel(
                totalCalories = 2000,
                caloriesLeft = 1500,
                totalCarbohydrates = 150,
                carbohydratesLeft = 100,
                totalProtein = 100,
                proteinLeft = 10,
                totalFat = 50,
                fatLeft = 0,
            ))
        }
    }
}
