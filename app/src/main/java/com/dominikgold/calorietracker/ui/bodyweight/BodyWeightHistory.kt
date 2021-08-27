package com.dominikgold.calorietracker.ui.bodyweight

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.entities.TimeInterval
import com.dominikgold.calorietracker.theming.TextStyles
import com.dominikgold.calorietracker.util.format
import com.dominikgold.calorietracker.util.rangeRoundedToNearestFive
import com.dominikgold.calorietracker.util.translated
import com.dominikgold.compose.linecharts.SimpleLineChart
import com.dominikgold.compose.linecharts.models.SimpleLineChartConfig
import com.dominikgold.compose.linecharts.models.SimpleLineChartDataPoint
import com.dominikgold.compose.linecharts.models.SimpleLineChartState
import com.dominikgold.compose.linecharts.models.rememberSimpleLineChartState
import kotlin.math.roundToInt

@Composable
fun BodyWeightHistory(bodyWeightHistory: BodyWeightHistoryUiModel) {
    val lineChartState = rememberSimpleLineChartState()
    lineChartState.updateDataPoints(
        simpleDataPoints = bodyWeightHistory.averagesForLineChart.mapIndexed { index, averageBodyWeight ->
            SimpleLineChartDataPoint(
                yAxisValue = averageBodyWeight,
                description = bodyWeightHistory.timeInterval
                    .getTimeAgoText(timePeriodIndex = bodyWeightHistory.averagesForLineChart.lastIndex - index),
            )
        },
        customDataRange = bodyWeightHistory.averagesForLineChart.rangeRoundedToNearestFive()
    )
    Column(Modifier.fillMaxSize()) {
        BodyWeightHistoryTitle(bodyWeightHistory)
        Spacer(modifier = Modifier.height(24.dp))
        BodyWeightHistoryLineChart(lineChartState)
        Spacer(modifier = Modifier.height(24.dp))
        BodyWeightHistoryList(bodyWeightHistory)
    }
}

@Composable
fun BodyWeightHistoryTitle(bodyWeightHistory: BodyWeightHistoryUiModel) {
    val titleFormatString = when (bodyWeightHistory.timeInterval) {
        TimeInterval.Monthly -> R.string.past_body_weight_periods_months_title
        TimeInterval.Weekly -> R.string.past_body_weight_periods_weeks_title
    }
    val titleText = translated(titleFormatString, formatArgs = listOf(bodyWeightHistory.periodCount))
    Text(text = titleText, style = TextStyles.Headline, modifier = Modifier.padding(horizontal = 24.dp))
}

@Composable
fun BodyWeightHistoryLineChart(lineChartState: SimpleLineChartState) {
    SimpleLineChart(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        lineChartState = lineChartState,
        config = SimpleLineChartConfig(
            chartScaffoldColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
            yAxisLabelsText = { Text(text = it.roundToInt().toString(), style = TextStyles.SubtitleSmall) }
        ),
        hoverPopup = { dataPoint ->
            Card(elevation = 8.dp) {
                Column(Modifier.padding(8.dp)) {
                    Text(text = dataPoint.yAxisValue.format(digitsAfterDecimal = 1))
                    dataPoint.description?.let { Text(text = it, style = TextStyles.Subtitle) }
                }
            }
        },
    )
}

@Composable
fun BodyWeightHistoryList(bodyWeightHistory: BodyWeightHistoryUiModel) {
    Column(Modifier.padding(horizontal = 24.dp)) {
        bodyWeightHistory.unfilteredAverages.forEachIndexed { index, averageBodyWeight ->
            Spacer(modifier = Modifier.height(8.dp))
            PastBodyWeightPeriod(index, averageBodyWeight, bodyWeightHistory.timeInterval)
        }
    }
}

@Composable
fun PastBodyWeightPeriod(index: Int, averageBodyWeight: Double?, timeInterval: TimeInterval) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = timeInterval.getTimeAgoText(timePeriodIndex = index),
            style = TextStyles.Title,
            modifier = Modifier.weight(1f),
        )
        Text(text = averageBodyWeight?.format() ?: "-", style = TextStyles.NumberInformation)
    }
}