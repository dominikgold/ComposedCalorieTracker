package com.dominikgold.calorietracker.ui.home

import androidx.compose.material.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.theming.ClearGreen
import com.dominikgold.calorietracker.theming.TextStyles
import com.dominikgold.calorietracker.theming.WarningRed
import com.dominikgold.calorietracker.theming.components.ElevatedButton
import com.dominikgold.calorietracker.util.Translated
import kotlin.math.absoluteValue

@Composable
fun NoCalorieGoalSet(onSetCalorieGoalClicked: () -> Unit) {
    Column(Modifier
               .fillMaxWidth()
               .padding(32.dp)) {
        Text(
            text = Translated(R.string.home_screen_no_calorie_goal_set_message),
            textAlign = TextAlign.Center,
            style = TextStyles.Title,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        ElevatedButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = Translated(R.string.set_first_calorie_goal_button),
            onClick = onSetCalorieGoalClicked,
        )
    }
}

@Composable
fun HomeScreenCalorieGoal(uiModel: CalorieGoalUiModel) {
    Column(Modifier.fillMaxWidth()) {
        AmountLeftText(
            name = Translated(resourceId = R.string.home_screen_calories_name),
            isExceeded = uiModel.isExceeded,
            amountLeft = uiModel.caloriesLeft,
        )
        uiModel.carbohydratesGoal?.let {
            Spacer(Modifier.height(8.dp))
            AmountLeftText(
                name = Translated(resourceId = R.string.home_screen_carbs_name),
                isExceeded = it.isExceeded,
                amountLeft = it.amountLeft,
            )
        }
        uiModel.proteinGoal?.let {
            Spacer(Modifier.height(8.dp))
            AmountLeftText(
                name = Translated(resourceId = R.string.home_screen_protein_name),
                isExceeded = it.isExceeded,
                amountLeft = it.amountLeft,
            )
        }
        uiModel.fatGoal?.let {
            Spacer(Modifier.height(8.dp))
            AmountLeftText(
                name = Translated(resourceId = R.string.home_screen_fat_name),
                isExceeded = it.isExceeded,
                amountLeft = it.amountLeft,
            )
        }
    }
}

@Composable
private fun AmountLeftText(name: String, isExceeded: Boolean, amountLeft: Int) {
    Row {
        Text(text = Translated(if (isExceeded) {
            R.string.home_screen_amount_exceeded
        } else {
            R.string.home_screen_amount_left
        }, listOf(name)), modifier = Modifier.align(Alignment.CenterVertically))

        Spacer(modifier = Modifier.width(16.dp))

        val amountLeftTextStyle = if (isExceeded) {
            TextStyles.NumberInformation.WarningRed
        } else {
            TextStyles.NumberInformation.ClearGreen
        }
        Text(
            text = amountLeft.absoluteValue.toString(),
            style = amountLeftTextStyle,
            modifier = Modifier.align(Alignment.CenterVertically),
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
                carbohydratesGoal = MacroGoalUiModel(150, 100),
                proteinGoal = MacroGoalUiModel(100, 10),
                fatGoal = MacroGoalUiModel(50, 0),
            ))
        }
    }
}
