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

val lightGreyDay = Color(0xFFFAF5F0)
val lightGreyNight = Color(0xFF212121)

val textColorDefault
    @Composable get() = if (LocalIsDarkTheme.current) textColorDefaultNight else textColorDefaultDay

val textColorSubtitle
    @Composable get() = if (LocalIsDarkTheme.current) textColorSubtitleNight else textColorSubtitleDay

val primaryBlue
    @Composable get() = if (LocalIsDarkTheme.current) primaryBlueNight else primaryBlueDay

val warningRed
    @Composable get() = if (LocalIsDarkTheme.current) warningRedNight else warningRedDay

val clearGreen
    @Composable get() = if (LocalIsDarkTheme.current) clearGreenNight else clearGreenDay

val colorDisabled
    @Composable get() = if (LocalIsDarkTheme.current) colorDisabledNight else colorDisabledDay

val lightGrey
    @Composable get() = if (LocalIsDarkTheme.current) lightGreyNight else lightGreyDay
