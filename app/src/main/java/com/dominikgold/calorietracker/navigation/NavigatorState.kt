package com.dominikgold.calorietracker.navigation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NavigatorState(val backStack: List<Screen>, val currentScreen: Screen) : Parcelable