package com.dominikgold.calorietracker.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun translated(@StringRes resourceId: Int, formatArgs: List<Any>? = null) =
    LocalContext.current.getString(resourceId, *(formatArgs?.toTypedArray() ?: arrayOf()))

@Composable
fun translated(@StringRes resourceId: Int?, formatArgs: List<Any>? = null) =
    resourceId?.let { LocalContext.current.getString(it, *(formatArgs?.toTypedArray() ?: arrayOf())) }
