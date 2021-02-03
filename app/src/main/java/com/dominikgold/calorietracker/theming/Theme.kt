package com.dominikgold.calorietracker.theming

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.staticAmbientOf
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = calorieTrackerBlueNight,
    primaryVariant = calorieTrackerBlueNight,
    onPrimary = Color.White,
    secondary = calorieTrackerBlueNight,
    onSecondary = Color.White,
    background = backgroundNight,
    surface = backgroundNight,
    onSurface = textColorDefaultNight,
    onBackground = textColorDefaultNight,
)

private val LightColorPalette = lightColors(
    primary = calorieTrackerBlueDay,
    primaryVariant = calorieTrackerBlueDay,
    onPrimary = Color.White,
    secondary = calorieTrackerBlueDay,
    onSecondary = Color.White,
    background = backgroundDay,
    surface = backgroundDay,
    onSurface = textColorDefaultDay,
    onBackground = textColorDefaultDay,
)

@Composable
fun CalorieTrackerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    Providers(AmbientIsDarkTheme provides darkTheme) {
        MaterialTheme(colors = colors, typography = typography, shapes = shapes, content = content)
    }
}

val AmbientIsDarkTheme = staticAmbientOf { false }