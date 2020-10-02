package com.dominikgold.calorietracker.navigation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Screen : Parcelable {

    @Parcelize
    object Home : Screen()

    @Parcelize
    object SetCalorieGoal : Screen()

}
