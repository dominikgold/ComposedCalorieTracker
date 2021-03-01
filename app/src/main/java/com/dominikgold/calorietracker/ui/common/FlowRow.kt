package com.dominikgold.calorietracker.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import kotlin.math.roundToInt

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    verticalContentSpacing: Dp,
    horizontalContentSpacing: Dp,
    content: @Composable () -> Unit,
) {
    Layout(content = content, modifier = modifier) { measurables, constraints ->
        val horizontalContentSpacingPx = horizontalContentSpacing.toPx().roundToInt()
        val verticalContentSpacingPx = verticalContentSpacing.toPx().roundToInt()
        val childConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val contentRows = createFlowRowContentRows(
            measurables = measurables,
            constraints = childConstraints,
            horizontalContentSpacingPx = horizontalContentSpacingPx,
            verticalContentSpacingPx = verticalContentSpacingPx,
        )
        val lastRow = contentRows.lastOrNull()
        val totalHeight = lastRow?.let { it.largestHeight + it.yOffset } ?: 0
        layout(constraints.maxWidth, totalHeight) {
            contentRows.forEach { it.placeAll(placementScope = this) }
        }
    }
}

private fun createFlowRowContentRows(
    measurables: List<Measurable>,
    constraints: Constraints,
    horizontalContentSpacingPx: Int,
    verticalContentSpacingPx: Int,
): List<FlowRowContentRow> {
    val rows: MutableList<FlowRowContentRow> = mutableListOf()
    var currentRowItems: MutableList<FlowRowContentItem> = mutableListOf()
    var currentX = 0
    var currentY = 0
    measurables.forEach { measurable ->
        val placeable = measurable.measure(constraints)
        if (currentX + placeable.width > constraints.maxWidth) {
            val contentRow = FlowRowContentRow(currentRowItems, currentY)
            currentY += contentRow.largestHeight + verticalContentSpacingPx
            currentX = 0
            currentRowItems = mutableListOf()
            rows.add(contentRow)
        }
        if (currentY > constraints.maxHeight) {
            return@createFlowRowContentRows rows
        }
        currentRowItems.add(FlowRowContentItem(placeable, xPosition = currentX))
        currentX += placeable.width + horizontalContentSpacingPx
    }
    if (currentRowItems.isNotEmpty()) {
        rows.add(FlowRowContentRow(currentRowItems, currentY))
    }
    return rows
}

private data class FlowRowContentRow(val contentItems: List<FlowRowContentItem>, val yOffset: Int) {

    val largestHeight = contentItems.maxOf { it.placeable.height }

    fun placeAll(placementScope: Placeable.PlacementScope) {
        contentItems.forEach { contentItem ->
            val yPosition = if (contentItem.placeable.height < largestHeight) {
                (largestHeight - contentItem.placeable.height) / 2 + yOffset
            } else {
                yOffset
            }
            with(placementScope) {
                contentItem.placeable.place(contentItem.xPosition, yPosition)
            }
        }
    }

}

private data class FlowRowContentItem(val placeable: Placeable, val xPosition: Int)

@Preview
@Composable
fun FlowRowPreview() {
    CalorieTrackerTheme {
        Column(Modifier.background(MaterialTheme.colors.background)) {
            FlowRow(horizontalContentSpacing = 16.dp, verticalContentSpacing = 8.dp) {
                Text(text = "test text")
                Text(text = "numero uno")
                Button(onClick = { /*TODO*/ }) {
                    Text("hehe")
                }
                Text(text = "nummer zwei")
                Text(text = "number three")
                Text(text = "numéro quatre")
                Text(text = "nummer vijf")
                Text(text = "that's it")
            }
        }
    }
}