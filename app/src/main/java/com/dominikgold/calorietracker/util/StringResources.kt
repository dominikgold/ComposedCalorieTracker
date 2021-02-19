package com.dominikgold.calorietracker.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.ContextAmbient

@Composable
fun translated(@StringRes resourceId: Int, formatArgs: List<Any>? = null) =
    AmbientContext.current.getString(resourceId, *(formatArgs?.toTypedArray() ?: arrayOf()))

@Composable
fun translated(@StringRes resourceId: Int?, formatArgs: List<Any>? = null) =
    resourceId?.let { AmbientContext.current.getString(it, *(formatArgs?.toTypedArray() ?: arrayOf())) }
