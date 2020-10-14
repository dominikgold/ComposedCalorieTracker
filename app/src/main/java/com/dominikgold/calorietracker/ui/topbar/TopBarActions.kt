package com.dominikgold.calorietracker.ui.topbar

import androidx.compose.foundation.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import com.dominikgold.calorietracker.theming.colorTopBarButton
import com.dominikgold.calorietracker.theming.textColorSubtitle

@Composable
fun TopBarActionTextButton(text: String, enabled: Boolean, onClick: () -> Unit) {
    TextButton(onClick = onClick, enabled = enabled) {
        Text(text = text, color = if (enabled) colorTopBarButton else textColorSubtitle)
    }
}