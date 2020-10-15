package com.dominikgold.calorietracker.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ContextAmbient

@Composable
fun Translated(@StringRes resourceId: Int, formatArgs: List<Any>? = null) =
    ContextAmbient.current.getString(resourceId, formatArgs)

@Composable
fun Translated(@StringRes resourceId: Int?, formatArgs: List<Any>? = null) =
    resourceId?.let { ContextAmbient.current.getString(it, formatArgs) }
