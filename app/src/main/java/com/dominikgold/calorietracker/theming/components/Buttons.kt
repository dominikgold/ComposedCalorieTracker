package com.dominikgold.calorietracker.theming.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.dominikgold.calorietracker.theming.DisabledColor
import com.dominikgold.calorietracker.theming.TextStyles

@Composable
fun StandardTextButton(modifier: Modifier = Modifier, text: String, enabled: Boolean = true, onClick: () -> Unit) {
    TextButton(modifier = modifier, onClick = onClick, enabled = enabled) {
        Text(text = text, style = if (enabled) TextStyles.TextButton else TextStyles.TextButton.DisabledColor)
    }
}

@Composable
fun ElevatedButton(
    modifier: Modifier = Modifier,
    text: String,
    elevation: ButtonElevation = ButtonDefaults.elevation(),
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(modifier = modifier, onClick = onClick, elevation = elevation, enabled = enabled) {
        Text(text = text, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }
}