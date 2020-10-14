package com.dominikgold.calorietracker.ui.caloriegoal

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.entities.MacroSplit
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.theming.textColorDefault
import com.dominikgold.calorietracker.theming.textColorSubtitle
import com.dominikgold.calorietracker.util.Translated
import com.dominikgold.calorietracker.util.inLightAndDarkTheme

@Composable
fun ChooseMacroSplit(macroSplitUiModel: MacroSplitUiModel?, onMacroSplitChosen: (MacroSplit) -> Unit) {
    Column(Modifier.background(MaterialTheme.colors.background)) {
        val dropdownMenuExpanded = remember { mutableStateOf(false) }
        DropdownMenu(
            toggle = {
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = Translated(macroSplitUiModel?.name) ?: Translated(R.string.choose_macro_split_hint),
                        color = if (macroSplitUiModel == null) textColorSubtitle else textColorDefault,
                    )
                    Icon(asset = vectorResource(id = R.drawable.vec_icon_arrow_drop_down), tint = textColorDefault)
                }
            },
            expanded = dropdownMenuExpanded.value,
            onDismissRequest = { dropdownMenuExpanded.value = false },
            toggleModifier = Modifier.clickable(onClick = { dropdownMenuExpanded.value = !dropdownMenuExpanded.value })
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            dropdownModifier = Modifier.fillMaxWidth(),
        ) {
            MacroSplitDropdownItems(onItemSelected = {
                dropdownMenuExpanded.value = false
                onMacroSplitChosen(it)
            })
        }
        Spacer(modifier = Modifier.height(16.dp))
        MacroSplitValues(macroSplitUiModel = macroSplitUiModel)
    }
}

@Composable
private fun MacroSplitDropdownItems(onItemSelected: (MacroSplit) -> Unit) {
    MacroSplit.values()
        .forEach { macroSplit ->
            DropdownMenuItem(onClick = { onItemSelected(macroSplit) }) {
                Text(text = Translated(macroSplit.translatableName))
            }
        }
}

@Composable
private fun ColumnScope.MacroSplitValues(macroSplitUiModel: MacroSplitUiModel?) {
    Row {
        Text(text = Translated(resourceId = R.string.choose_macro_split_carbohydrates_title), color = textColorDefault)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = macroSplitUiModel?.formattedCarbohydratesAmount ?: "", color = textColorSubtitle)
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row {
        Text(text = Translated(resourceId = R.string.choose_macro_split_protein_title), color = textColorDefault)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = macroSplitUiModel?.formattedProteinAmount ?: "", color = textColorSubtitle)
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row {
        Text(text = Translated(resourceId = R.string.choose_macro_split_fat_title), color = textColorDefault)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = macroSplitUiModel?.formattedFatAmount ?: "", color = textColorSubtitle)
    }
}

@Composable
@Preview(showBackground = true)
fun ChooseMacroSplitPreview() {
    inLightAndDarkTheme {
        ChooseMacroSplit(MacroSplitUiModel(MacroSplit.HIGH_CARB, 2000)) {}
    }
}
