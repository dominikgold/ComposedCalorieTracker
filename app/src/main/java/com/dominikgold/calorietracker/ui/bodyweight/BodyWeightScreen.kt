package com.dominikgold.calorietracker.ui.bodyweight

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.entities.TimeInterval
import com.dominikgold.calorietracker.theming.TextStyles
import com.dominikgold.calorietracker.ui.bottomnav.CalorieTrackerBottomNavigation
import com.dominikgold.calorietracker.ui.topbar.CalorieTrackerTopBar
import com.dominikgold.calorietracker.util.DecimalNumberInputFilter
import com.dominikgold.calorietracker.util.InLightAndDarkTheme
import com.dominikgold.calorietracker.util.LengthInputFilter
import com.dominikgold.calorietracker.util.inputFilters
import com.dominikgold.calorietracker.util.translated
import com.dominikgold.compose.linecharts.SimpleLineChartDataPoint
import com.dominikgold.compose.linecharts.rememberSimpleLineChartState
import com.dominikgold.compose.viewmodel.viewModel

@Composable
fun BodyWeightScreen() {
    Scaffold(
        topBar = { CalorieTrackerTopBar(title = translated(R.string.body_weight_screen_title)) },
        bottomBar = { CalorieTrackerBottomNavigation() },
    ) { innerPadding ->
        val viewModel = viewModel<BodyWeightViewModel>()
        val uiState by viewModel.bodyWeightState.collectAsState()
        BodyWeightScreenContent(Modifier.padding(innerPadding), uiState, viewModel::onBodyWeightInputChanged)
    }
}

@Composable
private fun BodyWeightScreenContent(
    modifier: Modifier,
    uiState: BodyWeightState,
    onBodyWeightChanged: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(modifier
               .verticalScroll(scrollState)
               .fillMaxSize()
               .padding(vertical = 16.dp)) {
        BodyWeightInput(uiState.bodyWeightInput, onBodyWeightChanged)
        Spacer(modifier = Modifier.height(24.dp))
        BodyWeightHistory(bodyWeightHistory = uiState.bodyWeightHistory)
    }
}

@Composable
private fun BodyWeightInput(
    bodyWeightInput: String,
    onBodyWeightChanged: (String) -> Unit,
) {
    Row(Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp), verticalAlignment = CenterVertically) {
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

@Preview
@Composable
fun BodyWeightScreenContentPreview() {
    InLightAndDarkTheme {
        BodyWeightScreenContent(
            Modifier,
            BodyWeightState("80,3", BodyWeightHistoryUiModel(listOf(), TimeInterval.Weekly, 6)),
            {},
        )
    }
}
