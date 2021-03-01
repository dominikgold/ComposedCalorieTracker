package com.dominikgold.calorietracker.ui.caloriegoal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.entities.Grams
import com.dominikgold.calorietracker.entities.MacroSplit
import com.dominikgold.calorietracker.theming.TextStyles
import com.dominikgold.calorietracker.ui.common.TextDropdownToggle
import com.dominikgold.calorietracker.util.LengthInputFilter
import com.dominikgold.calorietracker.util.NaturalNumberInputFilter
import com.dominikgold.calorietracker.util.inLightAndDarkTheme
import com.dominikgold.calorietracker.util.inputFilters
import com.dominikgold.calorietracker.util.translated

@Composable
fun SetMacroGoals(uiState: SetCalorieGoalUiState, setCalorieGoalActions: SetCalorieGoalActions) {
    Column {
        var dropdownMenuExpanded by remember { mutableStateOf(false) }
        Box {
            TextDropdownToggle(
                text = translated(uiState.macroSplitName),
                placeholder = translated(R.string.choose_macro_split_hint),
                isExpanded = dropdownMenuExpanded,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { dropdownMenuExpanded = !dropdownMenuExpanded })
                    .padding(top = 8.dp, bottom = 8.dp),
            )
            DropdownMenu(
                expanded = dropdownMenuExpanded,
                onDismissRequest = { dropdownMenuExpanded = false },
                modifier = Modifier.fillMaxWidth(),
            ) {
                MacroSplitDropdownItems(onItemSelected = {
                    dropdownMenuExpanded = false
                    setCalorieGoalActions.updateChosenMacroSplit(it)
                })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        IndividualMacroGoals(uiState, setCalorieGoalActions)
    }
}

@Composable
private fun MacroSplitDropdownItems(onItemSelected: (MacroSplit?) -> Unit) {
    DropdownMenuItem(onClick = { onItemSelected(null) }) {
        Text(text = translated(R.string.dropdown_reset_macro_split))
    }
    MacroSplit.values().forEach { macroSplit ->
        DropdownMenuItem(onClick = { onItemSelected(macroSplit) }) {
            Text(text = translated(macroSplit.translatableName))
        }
    }
}

@Composable
private fun IndividualMacroGoals(uiState: SetCalorieGoalUiState, setCalorieGoalActions: SetCalorieGoalActions) {
    ConstraintLayout {
        val (carbsTitle, proteinTitle, fatTitle) = createRefs()
        val (carbsInput, proteinInput, fatInput) = createRefs() // TODO Unfortunately, creating an end barrier here results in a crash at runtime when recomposing - try again
        //  with a later version. For now, we'll have to make do with assuming that the Carbohydrates is always the
        //  longest.
        // val titleBarrier = createEndBarrier(carbsTitle, proteinTitle, fatTitle, margin = 32.dp)
        Text(
            modifier = Modifier.constrainAs(carbsTitle) {
                top.linkTo(carbsInput.top)
                bottom.linkTo(carbsInput.bottom)
            },
            text = translated(R.string.choose_macro_split_carbohydrates_title),
            style = TextStyles.Title,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            modifier = Modifier.constrainAs(proteinTitle) {
                width = Dimension.fillToConstraints
                top.linkTo(proteinInput.top)
                bottom.linkTo(proteinInput.bottom)
                centerHorizontallyTo(carbsTitle)
            },
            text = translated(R.string.choose_macro_split_protein_title),
            style = TextStyles.Title,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            modifier = Modifier.constrainAs(fatTitle) {
                width = Dimension.fillToConstraints
                top.linkTo(fatInput.top)
                bottom.linkTo(fatInput.bottom)
                centerHorizontallyTo(carbsTitle)
            },
            text = translated(R.string.choose_macro_split_fat_title),
            style = TextStyles.Title,
            overflow = TextOverflow.Ellipsis,
        )
        OutlinedTextField(
            value = uiState.carbohydratesInput?.toString() ?: "",
            onValueChange = inputFilters(NaturalNumberInputFilter(), LengthInputFilter(maxLength = 3)) { newInput ->
                setCalorieGoalActions.updateCarbohydrates(newInput.toIntOrNull())
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.constrainAs(carbsInput) {
                width = Dimension.value(80.dp)
                top.linkTo(parent.top)
                bottom.linkTo(proteinInput.top, margin = 8.dp)
                start.linkTo(carbsTitle.end, margin = 32.dp)
            },
        )
        OutlinedTextField(
            value = uiState.proteinInput?.toString() ?: "",
            onValueChange = inputFilters(NaturalNumberInputFilter(), LengthInputFilter(maxLength = 3)) { newInput ->
                setCalorieGoalActions.updateProtein(newInput.toIntOrNull())
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.constrainAs(proteinInput) {
                width = Dimension.value(80.dp)
                top.linkTo(carbsInput.bottom, margin = 8.dp)
                bottom.linkTo(fatInput.top, margin = 8.dp)
                start.linkTo(proteinTitle.end, margin = 32.dp)
            },
        )
        OutlinedTextField(
            value = uiState.fatInput?.toString() ?: "",
            onValueChange = inputFilters(NaturalNumberInputFilter(), LengthInputFilter(maxLength = 3)) { newInput ->
                setCalorieGoalActions.updateFat(newInput.toIntOrNull())
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.constrainAs(fatInput) {
                width = Dimension.value(80.dp)
                top.linkTo(proteinInput.bottom, margin = 8.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(fatTitle.end, margin = 32.dp)
            },
        )
    }
}

@Composable
@Preview
fun SetMacroGoalsPreview() {
    inLightAndDarkTheme {
        Box(Modifier.background(MaterialTheme.colors.background)) {
            SetMacroGoals(SetCalorieGoalUiState(2000, 150, 100, 50, MacroSplit.HIGH_CARB),
                          object : SetCalorieGoalActions {
                              override fun updateTdee(tdee: Int?) {}
                              override fun updateChosenMacroSplit(macroSplit: MacroSplit?) {}
                              override fun updateProtein(protein: Grams?) {}
                              override fun updateCarbohydrates(carbohydrates: Grams?) {}
                              override fun updateFat(fat: Grams?) {}
                          })
        }
    }
}
