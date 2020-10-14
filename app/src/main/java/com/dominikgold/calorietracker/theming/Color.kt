package com.dominikgold.calorietracker.theming

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val purple200 = Color(0xFFBB86FC)
val purple500 = Color(0xFF6200EE)
val purple700 = Color(0xFF3700B3)
val teal200 = Color(0xFF03DAC5)

val textColorDefaultDay = Color(0xFF121212)
val textColorDefaultNight = Color(0xFFEDEDED)

val textColorSubtitleDay = Color(0xFF464646)
val textColorSubtitleNight = Color(0xFFA8A8A8)

val colorTopBarButtonDay = Color(0xFFEDEDED)
val colorTopBarButtonNight = purple200

@Composable
val textColorDefault
    get() = if (IsDarkThemeAmbient.current) textColorDefaultNight else textColorDefaultDay

@Composable
val textColorSubtitle
    get() = if (IsDarkThemeAmbient.current) textColorSubtitleNight else textColorSubtitleDay

@Composable
val colorTopBarButton
    get() = if (IsDarkThemeAmbient.current) colorTopBarButtonNight else colorTopBarButtonDay