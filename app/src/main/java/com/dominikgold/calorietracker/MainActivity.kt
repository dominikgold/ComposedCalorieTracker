package com.dominikgold.calorietracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import com.dominikgold.calorietracker.navigation.NavigationStateContainer
import com.dominikgold.calorietracker.navigation.Screen
import com.dominikgold.calorietracker.navigation.ViewModelContainerAmbient
import com.dominikgold.calorietracker.theming.CalorieTrackerTheme
import com.dominikgold.calorietracker.ui.caloriegoal.SetCalorieGoalScreen
import com.dominikgold.calorietracker.ui.home.HomeScreen
import javax.inject.Inject

private const val EXTRA_CURRENT_SCREEN = "EXTRA_CURRENT_SCREEN"

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationStateContainer: NavigationStateContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as CalorieTrackerApplication).daggerAppComponent.inject(this)
        savedInstanceState?.getParcelable<Screen>(EXTRA_CURRENT_SCREEN)?.let(navigationStateContainer::switchScreen)
        setContent {
            CalorieTrackerTheme {
                CurrentScreen(navigationStateContainer = navigationStateContainer)
            }
        }
    }
}

@Composable
fun CurrentScreen(navigationStateContainer: NavigationStateContainer) {
    Providers(ViewModelContainerAmbient provides navigationStateContainer.viewModelContainer) {
        val screen = navigationStateContainer.currentScreen.collectAsState()
        when (screen.value) {
            Screen.Home -> HomeScreen()
            Screen.SetCalorieGoal -> SetCalorieGoalScreen()
        }
    }
}