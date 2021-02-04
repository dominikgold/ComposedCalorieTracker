package com.dominikgold.calorietracker.ui.topbar

import androidx.compose.runtime.Composable
import com.dominikgold.calorietracker.theming.components.StandardTextButton

@Composable
fun TopBarActionTextButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    StandardTextButton(text = text, onClick = onClick, enabled = enabled)
}