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

    @Composable
    val Title: TextStyle
        get() = TextStyle(
            color = textColorDefault,
            fontSize = 18.sp,
        )

    @Composable
    val Headline: TextStyle
        get() = TextStyle(
            color = textColorDefault,
            fontSize = 28.sp,
        )

    @Composable
    val Subtitle: TextStyle
        get() = TextStyle(
            color = textColorSubtitle,
            fontSize = 18.sp,
        )

    @Composable
    val SubtitleSmall: TextStyle
        get() = TextStyle(
            color = textColorSubtitle,
            fontSize = 16.sp,
        )

    @Composable
    val Caption: TextStyle
        get() = TextStyle(
            color = textColorSubtitle,
            fontSize = 12.sp,
        )

    @Composable
    val TextButton: TextStyle
        get() = TextStyle(
            color = primaryBlue,
            fontSize = 16.sp,
            letterSpacing = 0.75.sp,
            fontWeight = FontWeight.Bold,
        )

    @Composable
    val NumberInformation: TextStyle
        get() = TextStyle(
            color = textColorDefault,
            fontSize = 20.sp,
            letterSpacing = 0.5.sp,
            fontWeight = FontWeight.SemiBold,
        )

}

@Composable
val TextStyle.DisabledColor
    get() = this.copy(color = colorDisabled)

@Composable
val TextStyle.ClearGreen
    get() = this.copy(color = clearGreen)

@Composable
val TextStyle.WarningRed
    get() = this.copy(color = warningRed)
