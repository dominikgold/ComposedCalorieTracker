package com.dominikgold.calorietracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import com.dominikgold.calorietracker.navigation.Navigator
import com.dominikgold.calorietracker.navigation.Screen
import com.dominikgold.calorietracker.navigation.ViewModelContainer
import com.dominikgold.calorietracker.navigation.ViewModelContainerAmbient
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.ui.caloriegoal.SetCalorieGoalScreen
import com.dominikgold.calorietracker.ui.home.HomeScreen
import javax.inject.Inject

private const val EXTRA_CURRENT_SCREEN = "EXTRA_CURRENT_SCREEN"

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as CalorieTrackerApplication).daggerAppComponent.inject(this)
        savedInstanceState?.getParcelable<Screen>(EXTRA_CURRENT_SCREEN)?.let(navigator::switchScreen)
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
}

@Composable
fun Main(navigator: Navigator) {
    val screen = navigator.currentScreen.collectAsState()
    CurrentScreen(screen.value, navigator.viewModelContainer)
}

@Composable
fun CurrentScreen(screen: Screen, viewModelContainer: ViewModelContainer) {
    Providers(ViewModelContainerAmbient provides viewModelContainer) {
        when (screen) {
            Screen.Home -> HomeScreen()
            Screen.SetCalorieGoal -> SetCalorieGoalScreen()
        }
    }
}