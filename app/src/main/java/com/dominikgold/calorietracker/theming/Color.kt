package com.dominikgold.calorietracker.theming

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val backgroundDay = Color(0xFFFFFFFF)
val backgroundNight = Color(0xFF212121)

val primaryBlueDay = Color(0xFF2E7294)
val primaryBlueNight = Color(0xFF47B1E6)

val warningRedDay = Color(0xFFEA4C48)
val warningRedNight = Color(0xFFF45652)

val clearGreenDay = Color(0xFF2E9462)
val clearGreenNight = Color(0xFF27A159)

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
val primaryBlue
    get() = if (AmbientIsDarkTheme.current) primaryBlueNight else primaryBlueDay

@Composable
val warningRed
    get() = if (AmbientIsDarkTheme.current) warningRedNight else warningRedDay

@Composable
val clearGreen
    get() = if (AmbientIsDarkTheme.current) clearGreenNight else clearGreenDay

@Composable
val colorDisabled
    get() = if (AmbientIsDarkTheme.current) colorDisabledNight else colorDisabledDay
