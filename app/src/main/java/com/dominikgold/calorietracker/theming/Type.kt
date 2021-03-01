package com.dominikgold.calorietracker.theming

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
)

object TextStyles {

    val Title: TextStyle
        @Composable get() = TextStyle(
            color = textColorDefault,
            fontSize = 18.sp,
        )

    val Headline: TextStyle
        @Composable get() = TextStyle(
            color = textColorDefault,
            fontSize = 28.sp,
        )

    val Subtitle: TextStyle
        @Composable get() = TextStyle(
            color = textColorSubtitle,
            fontSize = 18.sp,
        )

    val SubtitleSmall: TextStyle
        @Composable get() = TextStyle(
            color = textColorSubtitle,
            fontSize = 16.sp,
        )

    val Caption: TextStyle
        @Composable get() = TextStyle(
            color = textColorSubtitle,
            fontSize = 12.sp,
        )

    val TextButton: TextStyle
        @Composable get() = TextStyle(
            color = primaryBlue,
            fontSize = 16.sp,
            letterSpacing = 0.75.sp,
            fontWeight = FontWeight.Bold,
        )

    val NumberInformation: TextStyle
        @Composable get() = TextStyle(
            color = textColorDefault,
            fontSize = 20.sp,
            letterSpacing = 0.5.sp,
            fontWeight = FontWeight.SemiBold,
        )

}

val TextStyle.DisabledColor
    @Composable get() = this.copy(color = colorDisabled)

val TextStyle.ClearGreen
    @Composable get() = this.copy(color = clearGreen)

val TextStyle.WarningRed
    @Composable get() = this.copy(color = warningRed)

val TextStyle.PrimaryBlue
    @Composable get() = this.copy(color = primaryBlue)
