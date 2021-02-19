package com.dominikgold.calorietracker.ui.bodyweight

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.theming.TextStyles
import com.dominikgold.calorietracker.ui.bottomnav.CalorieTrackerBottomNavigation
import com.dominikgold.calorietracker.ui.topbar.CalorieTrackerTopBar
import com.dominikgold.calorietracker.util.DecimalNumberInputFilter
import com.dominikgold.calorietracker.util.LengthInputFilter
import com.dominikgold.calorietracker.util.translated
import com.dominikgold.calorietracker.util.inputFilters
import com.dominikgold.compose.viewmodel.viewModel

@Composable
fun BodyWeightScreen() {
    Scaffold(
        topBar = { CalorieTrackerTopBar(title = translated(R.string.body_weight_screen_title)) },
        bottomBar = { CalorieTrackerBottomNavigation() },
    ) {
        val viewModel = viewModel<BodyWeightViewModel>()
        val uiState by viewModel.bodyWeightState.collectAsState()
        DisposableEffect(key1 = null, effect = {
            onDispose {
                viewModel.saveBodyWeight()
            }
        })
        BodyWeightScreenContent(uiState, viewModel::onBodyWeightInputChanged)
    }
}

@Composable
private fun BodyWeightScreenContent(uiState: BodyWeightState, onBodyWeightChanged: (String) -> Unit) {
    Column(Modifier
               .fillMaxSize()
               .padding(vertical = 16.dp)) {
        BodyWeightInput(uiState.bodyWeightInput, onBodyWeightChanged)
    }
}

@Composable
private fun BodyWeightInput(
    bodyWeightInput: String,
    onBodyWeightChanged: (String) -> Unit,
) {
    Row(Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = translated(R.string.todays_body_weight), style = TextStyles.Title, modifier = Modifier.weight(1f))
        OutlinedTextField(
            modifier = Modifier.width(80.dp),
            value = bodyWeightInput,
            onValueChange = inputFilters(DecimalNumberInputFilter(), LengthInputFilter(maxLength = 5)) { input ->
                onBodyWeightChanged(input)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = translated(R.string.kilo_grams_acronym), style = TextStyles.Title)
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticsScreenContentPreview() {
    BodyWeightScreenContent(BodyWeightState("80,3"), {})
}