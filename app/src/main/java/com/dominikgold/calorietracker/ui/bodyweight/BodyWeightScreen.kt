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
import androidx.compose.material.MaterialTheme
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
import com.dominikgold.calorietracker.entities.BodyWeightEntry
import com.dominikgold.calorietracker.entities.BodyWeightEntryPeriod
import com.dominikgold.calorietracker.entities.TimeInterval
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.theming.TextStyles
import com.dominikgold.calorietracker.ui.bottomnav.CalorieTrackerBottomNavigation
import com.dominikgold.calorietracker.ui.topbar.CalorieTrackerTopBar
import com.dominikgold.calorietracker.util.DecimalNumberInputFilter
import com.dominikgold.calorietracker.util.InLightAndDarkTheme
import com.dominikgold.calorietracker.util.LengthInputFilter
import com.dominikgold.calorietracker.util.format
import com.dominikgold.calorietracker.util.inputFilters
import com.dominikgold.calorietracker.util.translated
import com.dominikgold.compose.linecharts.SimpleLineChart
import com.dominikgold.compose.linecharts.SimpleLineChartDataPoint
import com.dominikgold.compose.linecharts.rememberSimpleLineChartState
import com.dominikgold.compose.viewmodel.viewModel
import java.time.LocalDate

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
    val lineChartState = rememberSimpleLineChartState()
    // TODO: the body weight line chart data should always contain 6 points - if there is no data for a certain period,
    //  it should reuse the data from the period before
    lineChartState.updateDataPoints(uiState.pastBodyWeightPeriods.mapNotNull { bodyWeightEntryPeriod ->
        bodyWeightEntryPeriod.average?.let { SimpleLineChartDataPoint(it) }
    }.reversed())
    Column(modifier
               .verticalScroll(scrollState)
               .fillMaxSize()
               .padding(vertical = 16.dp)) {
        BodyWeightInput(uiState.bodyWeightInput, onBodyWeightChanged)
        Spacer(modifier = Modifier.height(24.dp))
        PastBodyWeightPeriodsTitle(uiState.pastBodyWeightPeriods)
        Spacer(modifier = Modifier.height(24.dp))
        PastBodyWeightPeriodsGraph(lineChartState)
        Spacer(modifier = Modifier.height(24.dp))
        PastBodyWeightPeriodsList(uiState.pastBodyWeightPeriods)
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

@Composable
fun PastBodyWeightPeriodsTitle(bodyWeightEntryPeriods: List<BodyWeightEntryPeriod>) {
    val titleFormatString = when (bodyWeightEntryPeriods.firstOrNull()?.timeInterval) {
        TimeInterval.Monthly -> R.string.past_body_weight_periods_months_title
        TimeInterval.Weekly -> R.string.past_body_weight_periods_weeks_title
        null -> null
    }
    val titleText = translated(titleFormatString, formatArgs = listOf(bodyWeightEntryPeriods.size)) ?: ""
    Text(text = titleText, style = TextStyles.Headline, modifier = Modifier.padding(horizontal = 24.dp))
}

@Composable
fun PastBodyWeightPeriodsGraph(lineChartState: com.dominikgold.compose.linecharts.SimpleLineChartState) {
    SimpleLineChart(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        lineChartState = lineChartState,
        chartScaffoldColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
    )
}

@Composable
fun PastBodyWeightPeriodsList(bodyWeightEntryPeriods: List<BodyWeightEntryPeriod>) {
    Column(Modifier.padding(horizontal = 24.dp)) {
        bodyWeightEntryPeriods.forEachIndexed { index, bodyWeightEntryPeriod ->
            Spacer(modifier = Modifier.height(8.dp))
            PastBodyWeightPeriod(index, bodyWeightEntryPeriod)
        }
    }
}

@Composable
fun PastBodyWeightPeriod(index: Int, bodyWeightEntryPeriod: BodyWeightEntryPeriod) {
    val formatString = when (bodyWeightEntryPeriod.timeInterval) {
        TimeInterval.Weekly -> R.string.past_body_weight_period_weekly_time_interval_format
        TimeInterval.Monthly -> R.string.past_body_weight_period_monthly_time_interval_format
    }
    val timeAgoText = translated(formatString, formatArgs = listOf(index + 1))
    Row(Modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {
        Text(text = timeAgoText, style = TextStyles.Title, modifier = Modifier.weight(1f))
        Text(text = bodyWeightEntryPeriod.average?.format() ?: "-", style = TextStyles.NumberInformation)
    }
}

@Preview
@Composable
fun BodyWeightScreenContentPreview() {
    CalorieTrackerTheme {
        BodyWeightScreenContent(Modifier, BodyWeightState("80,3", listOf()), {})
    }
}

@Preview(showBackground = true)
@Composable
fun PastBodyWeightPeriodsPreview() {
    InLightAndDarkTheme {
        Column {
            PastBodyWeightPeriodsTitle(listOf(
                BodyWeightEntryPeriod(TimeInterval.Weekly, listOf(BodyWeightEntry(LocalDate.now(), 90.0))),
                BodyWeightEntryPeriod(TimeInterval.Weekly, listOf(BodyWeightEntry(LocalDate.now(), 89.0))),
                BodyWeightEntryPeriod(TimeInterval.Weekly, listOf(BodyWeightEntry(LocalDate.now(), 88.0))),
            ))
            PastBodyWeightPeriodsTitle(listOf(
                BodyWeightEntryPeriod(TimeInterval.Monthly, listOf(BodyWeightEntry(LocalDate.now(), 90.0))),
                BodyWeightEntryPeriod(TimeInterval.Monthly, listOf(BodyWeightEntry(LocalDate.now(), 89.0))),
                BodyWeightEntryPeriod(TimeInterval.Monthly, listOf(BodyWeightEntry(LocalDate.now(), 88.0))),
            ))
        }
    }
}