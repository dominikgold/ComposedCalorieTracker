package com.dominikgold.calorietracker.theming

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val backgroundDay = Color(0xFFFFFFFF)
val backgroundNight = Color(0xFF212121)

val calorieTrackerBlueDay = Color(0xFF2E7294)
val calorieTrackerBlueNight = Color(0xFF47B1E6)

val textColorDefaultDay = Color(0xFF121212)
val textColorDefaultNight = Color(0xFFFDFDFD)

val textColorSubtitleDay = Color(0xFF535353)
val textColorSubtitleNight = Color(0xFFB8B8B8)

val colorDisabledDay = Color(0xFFE1DDD8)
val colorDisabledNight = Color(0xFF646260)

@Composable
val textColorDefault
    get() = if (AmbientIsDarkTheme.current) textColorDefaultNight else textColorDefaultDay

@Composable
val textColorSubtitle
    get() = if (AmbientIsDarkTheme.current) textColorSubtitleNight else textColorSubtitleDay

@Composable
val calorieTrackerBlue
    get() = if (AmbientIsDarkTheme.current) calorieTrackerBlueNight else calorieTrackerBlueDay

@Composable
val colorDisabled
    get() = if (AmbientIsDarkTheme.current) colorDisabledNight else colorDisabledDay
