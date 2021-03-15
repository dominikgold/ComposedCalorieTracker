package com.dominikgold.calorietracker.util

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme

@Composable
fun InLightAndDarkTheme(previewContent: @Composable () -> Unit) {
    Column {
        CalorieTrackerTheme(darkTheme = false, content = previewContent)
        Spacer(modifier = Modifier.height(32.dp))
        CalorieTrackerTheme(darkTheme = true, content = previewContent)
    }
}