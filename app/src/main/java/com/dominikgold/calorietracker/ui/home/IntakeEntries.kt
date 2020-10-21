package com.dominikgold.calorietracker.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayout
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.theming.textColorSubtitle
import com.dominikgold.calorietracker.util.LengthInputFilter
import com.dominikgold.calorietracker.util.Translated

@OptIn(ExperimentalLayout::class)
@Composable
fun IntakeEntryCard(uiModel: IntakeEntryUiModel) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(8.dp)) {
            Text(text = uiModel.name)
            Spacer(Modifier.height(8.dp))
            FlowRow(mainAxisSpacing = 16.dp, crossAxisSpacing = 8.dp) {
                Text(
                    text = Translated(R.string.intake_entry_calorie_count, listOf(uiModel.calories)),
                    color = textColorSubtitle,
                )
                Text(
                    text = Translated(R.string.intake_entry_carbs_count, listOf(uiModel.carbohydrates)),
                    color = textColorSubtitle,
                )
                Text(
                    text = Translated(R.string.intake_entry_protein_count, listOf(uiModel.protein)),
                    color = textColorSubtitle,
                )
                Text(
                    text = Translated(R.string.intake_entry_fat_count, listOf(uiModel.fat)),
                    color = textColorSubtitle,
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedAddIntakeEntry(
    isVisible: Boolean,
    enterAnimationDelay: Int,
    onConfirmed: (IntakeEntryUiModel) -> Unit,
    onCancelled: () -> Unit,
) {
    val expandVertically = expandVertically(animSpec = TweenSpec(durationMillis = 400, delay = enterAnimationDelay))
    val fadeIn = fadeIn(animSpec = TweenSpec(durationMillis = 400, delay = enterAnimationDelay))
    val fadeOut = fadeOut(animSpec = TweenSpec(durationMillis = 400))
    val shrinkVertically = shrinkVertically(animSpec = TweenSpec(durationMillis = 400))
    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically + fadeIn,
        exit = fadeOut + shrinkVertically,
    ) {
        AddIntakeEntry(onConfirmed = onConfirmed, onCancelled = onCancelled)
    }
}

@OptIn(ExperimentalLayout::class)
@Composable
fun AddIntakeEntry(onConfirmed: (IntakeEntryUiModel) -> Unit, onCancelled: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            val nameInput = remember { mutableStateOf("") }
            val caloriesInput = remember { mutableStateOf<Int?>(null) }
            val carbohydratesInput = remember { mutableStateOf<Int?>(null) }
            val proteinInput = remember { mutableStateOf<Int?>(null) }
            val fatInput = remember { mutableStateOf<Int?>(null) }
            TextField(
                value = nameInput.value,
                onValueChange = { nameInput.value = it },
                placeholder = {
                    Text(text = Translated(resourceId = R.string.intake_entry_name_input_placeholder))
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = caloriesInput.value?.toString() ?: "",
                onValueChange = LengthInputFilter(maxLength = 5) { newInput ->
                    caloriesInput.value = newInput.toIntOrNull()
                },
                placeholder = { Text(text = Translated(R.string.intake_entry_calorie_input_placeholder)) },
                keyboardType = KeyboardType.Number,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                AddIntakeEntryMacroField(
                    inputState = carbohydratesInput,
                    placeholderText = Translated(resourceId = R.string.intake_entry_carbohydrates_input_placeholder),
                )
                Spacer(modifier = Modifier.width(16.dp))
                AddIntakeEntryMacroField(
                    inputState = proteinInput,
                    placeholderText = Translated(resourceId = R.string.intake_entry_protein_input_placeholder),
                )
                Spacer(modifier = Modifier.width(16.dp))
                AddIntakeEntryMacroField(
                    inputState = fatInput,
                    placeholderText = Translated(resourceId = R.string.intake_entry_fat_input_placeholder),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(Modifier.align(Alignment.End)) {
                Button(onClick = onCancelled) {
                    Text(text = Translated(R.string.add_intake_entry_button_cancel))
                }
                Spacer(modifier = Modifier.width(8.dp))
                AddIntakeEntryConfirmButton(
                    name = nameInput.value,
                    calories = caloriesInput.value,
                    carbohydrates = carbohydratesInput.value,
                    protein = proteinInput.value,
                    fat = fatInput.value,
                    onConfirmed = onConfirmed,
                )
            }
        }
    }
}

@Composable
fun AddIntakeEntryConfirmButton(
    name: String,
    calories: Int?,
    carbohydrates: Int?,
    protein: Int?,
    fat: Int?,
    onConfirmed: (IntakeEntryUiModel) -> Unit,
) {
    Button(
        onClick = {
            if (calories != null && carbohydrates != null && protein != null && fat != null) {
                onConfirmed(IntakeEntryUiModel(name, calories, carbohydrates, protein, fat))
            }
        },
        enabled = calories != null && carbohydrates != null && protein != null && fat != null,
    ) {
        Text(text = Translated(R.string.add_intake_entry_button_confirm))
    }
}

@Composable
private fun RowScope.AddIntakeEntryMacroField(inputState: MutableState<Int?>, placeholderText: String) {
    TextField(
        value = inputState.value?.toString() ?: "",
        onValueChange = LengthInputFilter(maxLength = 5) { newInput ->
            inputState.value = newInput.toIntOrNull()
        },
        placeholder = { Text(text = placeholderText) },
        keyboardType = KeyboardType.Number,
        modifier = Modifier.weight(1f),
    )
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

@Preview
@Composable
fun AddIntakeEntryPreview() {
    CalorieTrackerTheme {
        Box(Modifier.background(Color.White)) {
            AddIntakeEntry(onConfirmed = {}, onCancelled = {})
        }
    }
}