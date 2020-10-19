package com.dominikgold.calorietracker.ui.home

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayout
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.theming.textColorSubtitle
import com.dominikgold.calorietracker.util.Translated

@OptIn(ExperimentalLayout::class)
@Composable
fun IntakeEntryCard(uiModel: IntakeEntryUiModel) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = 4.dp) {
        Column(Modifier.padding(8.dp)) {
            Text(text = uiModel.name)
            FlowRow {
                Text(
                    text = Translated(R.string.intake_entry_calorie_count, listOf(uiModel.calories)),
                    color = textColorSubtitle,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = Translated(R.string.intake_entry_carbs_count, listOf(uiModel.carbohydrates)),
                    color = textColorSubtitle,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = Translated(R.string.intake_entry_protein_count, listOf(uiModel.protein)),
                    color = textColorSubtitle,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = Translated(R.string.intake_entry_fat_count, listOf(uiModel.fat)),
                    color = textColorSubtitle,
                )
            }
        }
    }
}

@Preview
@Composable
fun IntakeEntryCardPreview() {
    CalorieTrackerTheme {
        Column {
            IntakeEntryCard(uiModel = IntakeEntryUiModel("protein.", 1000, 0, 250, 0))
        }
    }
}