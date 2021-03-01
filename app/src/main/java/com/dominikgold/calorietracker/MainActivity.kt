package com.dominikgold.calorietracker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import com.dominikgold.calorietracker.navigation.Navigator
import com.dominikgold.calorietracker.navigation.NavigatorState
import com.dominikgold.calorietracker.navigation.Screen
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.ui.bodyweight.BodyWeightScreen
import com.dominikgold.calorietracker.ui.caloriegoal.SetCalorieGoalScreen
import com.dominikgold.calorietracker.ui.home.HomeScreen
import com.dominikgold.calorietracker.ui.settings.SettingsScreen
import com.dominikgold.compose.viewmodel.LocalViewModelContainer
import javax.inject.Inject

private const val EXTRA_NAVIGATOR_STATE = "EXTRA_NAVIGATOR_STATE"

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as CalorieTrackerApplication).daggerAppComponent.inject(this)
        savedInstanceState?.getParcelable<NavigatorState>(EXTRA_NAVIGATOR_STATE)?.let(navigator::restoreState)
        setContent {
            CalorieTrackerTheme {
                Main(navigator = navigator)
            }
        }
    }

    override fun onBackPressed() {
        if (!navigator.goBack()) {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_NAVIGATOR_STATE, navigator.saveState())
    }
}

@Composable
fun Main(navigator: Navigator) {
    val navigationStateEntry = navigator.currentNavigationStateEntry.collectAsState()
    CompositionLocalProvider(LocalViewModelContainer provides navigationStateEntry.value.viewModelContainer) {
        when (navigationStateEntry.value.screen) {
            Screen.Home -> HomeScreen()
            Screen.Statistics -> BodyWeightScreen()
            Screen.Settings -> SettingsScreen()
            Screen.SetCalorieGoal -> SetCalorieGoalScreen()
        }
    }
}
