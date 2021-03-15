package com.dominikgold.calorietracker.ui.common.linechart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.dp
import com.dominikgold.calorietracker.util.InLightAndDarkTheme
import kotlin.math.roundToInt

@Composable
fun SimpleLineChart(
    dataPoints: List<SimpleLineChartDataPoint>,
    modifier: Modifier = Modifier,
    chartScaffoldColor: Color = MaterialTheme.colors.onBackground,
    lineColor: Color = MaterialTheme.colors.primary,
) {
    SimpleLineChartInternal(
        dataPoints = NormalizedSimpleLineChartDataPoints(dataPoints, padding = 0.1),
        modifier = modifier,
        chartScaffoldColor = chartScaffoldColor,
        lineColor = lineColor,
    )
}

@Composable
private fun SimpleLineChartInternal(
    dataPoints: NormalizedSimpleLineChartDataPoints,
    modifier: Modifier = Modifier,
    chartScaffoldColor: Color = MaterialTheme.colors.onBackground,
    lineColor: Color = MaterialTheme.colors.primary,
) {
    BoxWithConstraints(modifier) {
        val chartWidth = constraints.maxWidth
        val chartHeight = constraints.constrainHeight((chartWidth * 0.75).roundToInt())
        with(LocalDensity.current) {
            val chartOutlineWidth = 1.dp.toPx()
            val lineGraphWidth = 2.dp.toPx()
            Canvas(modifier = Modifier.size(chartWidth.toFloat().toDp(), chartHeight.toFloat().toDp())) {
                drawChartBackground(chartScaffoldColor, chartOutlineWidth)
                drawLineGraph(dataPoints, lineColor, lineGraphWidth, chartOutlineWidth)
            }
        }
    }
}

fun DrawScope.drawLineGraph(
    dataPoints: NormalizedSimpleLineChartDataPoints,
    lineColor: Color,
    lineGraphWidth: Float,
    chartOutlineWidth: Float,
) {
    val heightWithoutOutline = this.size.height - chartOutlineWidth
    if (dataPoints.normalizedYAxisValues.size == 1) {
        drawLine(lineColor,
                 start = Offset(chartOutlineWidth, heightWithoutOutline / 2f),
                 end = Offset(this.size.width, heightWithoutOutline / 2f),
                 strokeWidth = lineGraphWidth)
        return
    }

    val widthWithoutOutline = this.size.width - chartOutlineWidth
    drawPoints(
        points = dataPoints.normalizedYAxisValues.mapIndexed { index, yAxisPercentage ->
            // The y axis value needs to be inverted as the given percentages are from bottom to top while the on screen
            //  representation is from top to bottom
            val invertedYAxisPercentage = 1.0 - yAxisPercentage
            val xAxisPercentage = index.toFloat() / (dataPoints.normalizedYAxisValues.size - 1).toFloat()
            return@mapIndexed Offset(
                x = chartOutlineWidth + widthWithoutOutline * xAxisPercentage,
                y = heightWithoutOutline * invertedYAxisPercentage.toFloat(),
            )
        },
        pointMode = PointMode.Polygon,
        color = lineColor,
        strokeWidth = lineGraphWidth,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawChartBackground(color: Color, outlineWidth: Float) {
    drawLine(
        color,
        start = Offset.Zero,
        end = Offset(0f, this.size.height - 16f - outlineWidth),
        strokeWidth = outlineWidth,
    )
    drawArc(
        color,
        startAngle = 180f,
        sweepAngle = -90f,
        useCenter = false,
        topLeft = Offset(0f, this.size.height - 32f - outlineWidth),
        size = Size(32f, 32f),
        style = Stroke(width = outlineWidth),
    )
    drawLine(
        color,
        start = Offset(16f, this.size.height - outlineWidth),
        end = Offset(this.size.width, this.size.height - outlineWidth),
        strokeWidth = outlineWidth,
    )
}

@Preview(showBackground = true)
@Composable
fun SimpleLineChartPreview() {
    InLightAndDarkTheme {
        Box(Modifier
                .background(MaterialTheme.colors.background)
                .padding(16.dp)) {
            SimpleLineChart(dataPoints = listOf(
                SimpleLineChartDataPoint(yAxisValue = 90.0, "6 w ago"),
                SimpleLineChartDataPoint(yAxisValue = 89.0, "5 w ago"),
                SimpleLineChartDataPoint(yAxisValue = 91.0, "4 w ago"),
                SimpleLineChartDataPoint(yAxisValue = 93.0, "3 w ago"),
                SimpleLineChartDataPoint(yAxisValue = 90.0, "2 w ago"),
                SimpleLineChartDataPoint(yAxisValue = 92.0, "1 w ago"),
            ))
        }
    }
}
