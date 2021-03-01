package com.dominikgold.calorietracker.theming

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = primaryBlueNight,
    primaryVariant = primaryBlueNight,
    onPrimary = Color.White,
    secondary = primaryBlueNight,
    onSecondary = Color.White,
    background = backgroundNight,
    surface = backgroundNight,
    onSurface = textColorDefaultNight,
    onBackground = textColorDefaultNight,
)

private val LightColorPalette = lightColors(
    primary = primaryBlueDay,
    primaryVariant = primaryBlueDay,
    onPrimary = Color.White,
    secondary = primaryBlueDay,
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

    CompositionLocalProvider(LocalIsDarkTheme provides darkTheme) {
        MaterialTheme(colors = colors, typography = typography, shapes = shapes, content = content)
    }
}

val LocalIsDarkTheme = staticCompositionLocalOf { false }