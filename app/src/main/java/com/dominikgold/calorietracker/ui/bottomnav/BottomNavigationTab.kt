package com.dominikgold.calorietracker.ui.bottomnav

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.vectorResource
import com.dominikgold.calorietracker.R

enum class BottomNavigationTab {

    HOME,
    STATISTICS,
    SETTINGS,

}

@Composable
val BottomNavigationTab.title: String
    get() = AmbientContext.current.getString(when (this) {
                                                 BottomNavigationTab.HOME -> R.string.home_tab_title
                                                 BottomNavigationTab.STATISTICS -> R.string.statistics_tab_title
                                                 BottomNavigationTab.SETTINGS -> R.string.settings_tab_title
                                             })

@Composable
val BottomNavigationTab.icon: ImageVector
    get() = vectorResource(id = when (this) {
        BottomNavigationTab.HOME -> R.drawable.vec_icon_home_tab
        BottomNavigationTab.STATISTICS -> R.drawable.vec_icon_statistics_tab
        BottomNavigationTab.SETTINGS -> R.drawable.vec_icon_settings_tab
    })