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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.theming.textColorSubtitle
import com.dominikgold.calorietracker.ui.CalorieTrackerTopBar
import com.dominikgold.calorietracker.util.LengthInputFilter
import com.dominikgold.calorietracker.util.Translated

@Composable
fun SetCalorieGoalScreen() {
    Scaffold(topBar = {
        CalorieTrackerTopBar(
            title = Translated(R.string.set_calorie_goal_title),
            navigationIcon = vectorResource(id = R.drawable.vec_icon_close),
        )
    }) {
        SetCalorieGoalContent()
    }
}

@Composable
fun SetCalorieGoalContent() {
    val tdeeInput = remember { mutableStateOf<Int?>(null) }
    Column(Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        TextField(
            value = tdeeInput.value?.toString() ?: "",
            placeholder = {
                Text(text = Translated(resourceId = R.string.tdee_input_placeholder))
            },
            onValueChange = LengthInputFilter(maxLength = 5) { newInput ->
                newInput.toIntOrNull()?.let { tdeeInput.value = it }
            },
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        ChooseMacroSplit(tdeeInput.value ?: 0)
    }
}

@Composable
@Preview(showBackground = true)
fun SetCalorieGoalContentPreview() {
    CalorieTrackerTheme {
        SetCalorieGoalContent()
    }
}
