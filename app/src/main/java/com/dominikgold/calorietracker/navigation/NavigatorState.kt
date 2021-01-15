package com.dominikgold.calorietracker.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NavigatorState(val bottomNavigationSavedState: BottomNavigationSavedState) : Parcelable