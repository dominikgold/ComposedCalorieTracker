package com.dominikgold.calorietracker.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ContextAmbient

@Composable
fun Translated(@StringRes resourceId: Int) = ContextAmbient.current.getString(resourceId)

@Composable
fun Translated(@StringRes resourceId: Int?) =
    resourceId?.let { ContextAmbient.current.getString(it) }
