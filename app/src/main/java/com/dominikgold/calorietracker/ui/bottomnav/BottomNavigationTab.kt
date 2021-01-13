package com.dominikgold.calorietracker.ui.bottomnav

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.vectorResource
import com.dominikgold.calorietracker.R
import com.dominikgold.calorietracker.navigation.Screen

enum class BottomNavigationTab {

    HOME,
    STATISTICS,
    SETTINGS,

}

val BottomNavigationTab.initialScreen: Screen
    get() = when (this) {
        BottomNavigationTab.HOME -> Screen.Home
        BottomNavigationTab.STATISTICS -> Screen.Statistics
        BottomNavigationTab.SETTINGS -> Screen.Settings
    }

@Composable
val BottomNavigationTab.title: String
    get() = ContextAmbient.current.getString(when (this) {
                                                 BottomNavigationTab.HOME -> R.string.home_tab_title
                                                 BottomNavigationTab.STATISTICS -> R.string.statistics_tab_title
                                                 BottomNavigationTab.SETTINGS -> R.string.settings_tab_title
                                             })

@Composable
val BottomNavigationTab.icon: VectorAsset
    get() = vectorResource(id = when (this) {
        BottomNavigationTab.HOME -> R.drawable.vec_icon_home_tab
        BottomNavigationTab.STATISTICS -> R.drawable.vec_icon_statistics_tab
        BottomNavigationTab.SETTINGS -> R.drawable.vec_icon_settings_tab
    })