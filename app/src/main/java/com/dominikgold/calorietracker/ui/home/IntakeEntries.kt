package com.dominikgold.calorietracker.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.theming.TextStyles
import com.dominikgold.calorietracker.ui.common.FlowRow
import com.dominikgold.calorietracker.util.LengthInputFilter
import com.dominikgold.calorietracker.util.NaturalNumberInputFilter
import com.dominikgold.calorietracker.util.InLightAndDarkTheme
import com.dominikgold.calorietracker.util.inputFilters
import com.dominikgold.calorietracker.util.translated

enum class IntakeEntryAnimationState {

    Added,
    Deleted,

}

fun IntakeEntryUiModel.animationState(
    currentIntakeEntries: List<IntakeEntryUiModel>,
    previousIntakeEntries: List<IntakeEntryUiModel>,
) = when {
    this in currentIntakeEntries && this !in previousIntakeEntries -> IntakeEntryAnimationState.Added
    this in previousIntakeEntries && this !in currentIntakeEntries -> IntakeEntryAnimationState.Deleted
    else -> null
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedIntakeEntryCard(
    uiModel: IntakeEntryUiModel,
    animationState: IntakeEntryAnimationState,
    animationDelay: Int,
    onIntakeEntryDeleted: (IntakeEntryUiModel) -> Unit,
) {
    val expandVertically = expandVertically(
        expandFrom = Alignment.Top,
        animationSpec = tween(durationMillis = 300),
    )
    val slideIn = slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(durationMillis = 600, delayMillis = 150 + animationDelay),
    )
    val slideOut = slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(durationMillis = 600, delayMillis = animationDelay),
    )
    val shrinkVertically = shrinkVertically(
        shrinkTowards = Alignment.Top,
        animationSpec = tween(durationMillis = 300, delayMillis = 450 + animationDelay),
    )
    AnimatedVisibility(
        visible = animationState == IntakeEntryAnimationState.Added,
        enter = expandVertically + slideIn,
        exit = slideOut + shrinkVertically,
        initiallyVisible = animationState != IntakeEntryAnimationState.Added,
    ) {
        IntakeEntryCard(uiModel = uiModel, onIntakeEntryDeleted = onIntakeEntryDeleted)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun IntakeEntryCard(uiModel: IntakeEntryUiModel, onIntakeEntryDeleted: (IntakeEntryUiModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp),
        elevation = 2.dp,
    ) {
        Column {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = uiModel.name,
                    style = TextStyles.Title,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 16.dp, start = 16.dp),
                )
                IconButton(modifier = Modifier.padding(4.dp), onClick = { onIntakeEntryDeleted(uiModel) }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.vec_icon_delete_intake_entry),
                        contentDescription = null,
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Box(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                FlowRow(horizontalContentSpacing = 16.dp, verticalContentSpacing = 8.dp) {
                    Text(text = translated(R.string.intake_entry_calorie_count, listOf(uiModel.calories)),
                         style = TextStyles.SubtitleSmall)
                    uiModel.carbohydrates?.let { carbohydrates ->
                        Text(text = translated(R.string.intake_entry_carbs_count, listOf(carbohydrates)),
                             style = TextStyles.SubtitleSmall)
                    }
                    uiModel.protein?.let { protein ->
                        Text(text = translated(R.string.intake_entry_protein_count, listOf(protein)),
                             style = TextStyles.SubtitleSmall)
                    }
                    uiModel.fat?.let { fat ->
                        Text(text = translated(R.string.intake_entry_fat_count, listOf(fat)),
                             style = TextStyles.SubtitleSmall)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedAddIntakeEntry(
    isVisible: Boolean,
    enterAnimationDelay: Int,
    onConfirmed: (AddIntakeEntryUiModel) -> Unit,
    onCancelled: () -> Unit,
) {
    val expandVertically = expandVertically(animationSpec = TweenSpec(durationMillis = 400, enterAnimationDelay))
    val fadeIn = fadeIn(animationSpec = TweenSpec(durationMillis = 400, delay = enterAnimationDelay))
    val fadeOut = fadeOut(animationSpec = TweenSpec(durationMillis = 400))
    val shrinkVertically = shrinkVertically(animationSpec = TweenSpec(durationMillis = 400))
    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically + fadeIn,
        exit = fadeOut + shrinkVertically,
    ) {
        Box(Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
            AddIntakeEntry(onConfirmed = onConfirmed, onCancelled = onCancelled)
        }
    }
}

@Composable
fun AddIntakeEntry(onConfirmed: (AddIntakeEntryUiModel) -> Unit, onCancelled: () -> Unit) {
    var uiModel by mutableStateOf(AddIntakeEntryUiModel("", null, null, null, null))
    Card(modifier = Modifier.fillMaxWidth(), elevation = 2.dp) {
        Column(Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = uiModel.name,
                onValueChange = { uiModel = uiModel.copy(name = it) },
                placeholder = { Text(text = translated(resourceId = R.string.intake_entry_name_input_placeholder)) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = uiModel.calories?.toString() ?: "",
                onValueChange = inputFilters(NaturalNumberInputFilter(), LengthInputFilter(maxLength = 5)) { newInput ->
                    uiModel = uiModel.copy(calories = newInput.toIntOrNull())
                },
                placeholder = { Text(text = translated(R.string.intake_entry_calorie_input_placeholder)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                AddIntakeEntryMacroField(
                    currentValue = uiModel.carbohydrates,
                    onChanged = { uiModel = uiModel.copy(carbohydrates = it) },
                    placeholderText = translated(resourceId = R.string.intake_entry_carbohydrates_input_placeholder),
                )
                Spacer(modifier = Modifier.width(16.dp))
                AddIntakeEntryMacroField(
                    currentValue = uiModel.protein,
                    onChanged = { uiModel = uiModel.copy(protein = it) },
                    placeholderText = translated(resourceId = R.string.intake_entry_protein_input_placeholder),
                )
                Spacer(modifier = Modifier.width(16.dp))
                AddIntakeEntryMacroField(
                    currentValue = uiModel.fat,
                    onChanged = { uiModel = uiModel.copy(fat = it) },
                    placeholderText = translated(resourceId = R.string.intake_entry_fat_input_placeholder),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(Modifier.align(Alignment.End)) {
                Button(onClick = onCancelled) {
                    Text(text = translated(R.string.add_intake_entry_button_cancel))
                }
                Spacer(modifier = Modifier.width(8.dp))
                AddIntakeEntryConfirmButton(
                    uiModel = uiModel,
                    onConfirmed = { onConfirmed(uiModel) },
                )
            }
        }
    }
}

@Composable
fun AddIntakeEntryConfirmButton(
    uiModel: AddIntakeEntryUiModel,
    onConfirmed: () -> Unit,
) {
    Button(onClick = onConfirmed, enabled = uiModel.isConfirmButtonEnabled) {
        Text(text = translated(R.string.add_intake_entry_button_confirm))
    }
}

@Composable
private fun RowScope.AddIntakeEntryMacroField(
    currentValue: Int?,
    onChanged: (Int?) -> Unit,
    placeholderText: String,
) {
    OutlinedTextField(
        value = currentValue?.toString() ?: "",
        onValueChange = inputFilters(NaturalNumberInputFilter(), LengthInputFilter(maxLength = 5)) { newInput ->
            onChanged(newInput.toIntOrNull())
        },
        placeholder = { Text(text = placeholderText) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.weight(1f),
    )
}

@Preview
@Composable
fun IntakeEntryCardPreview() {
    InLightAndDarkTheme {
        Column {
            IntakeEntryCard(uiModel = IntakeEntryUiModel("", "protein.", 1000, 0, 250, 0), {})
            IntakeEntryCard(uiModel = IntakeEntryUiModel(
                "",
                "very very long food name that spans longer than one line.",
                300,
                null,
                null,
                null,
            ), {})
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