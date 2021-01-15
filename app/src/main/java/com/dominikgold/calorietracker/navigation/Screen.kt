package com.dominikgold.calorietracker.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Screen : Parcelable {

    @Parcelize
    object Home : Screen()

    @Parcelize
    object SetCalorieGoal : Screen()

    @Parcelize
    object Statistics : Screen()

    @Parcelize
    object Settings : Screen()
}
