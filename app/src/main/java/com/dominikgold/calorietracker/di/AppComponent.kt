package com.dominikgold.calorietracker.di

import android.content.Context
import com.dominikgold.calorietracker.MainActivity
import com.dominikgold.calorietracker.datasources.DataSourcesModule
import com.dominikgold.calorietracker.repositories.RepositoriesModule
import com.dominikgold.calorietracker.ui.bodyweight.BodyWeightUiModule
import com.dominikgold.calorietracker.ui.bottomnav.BottomNavigationUiModule
import com.dominikgold.calorietracker.ui.caloriegoal.SetCalorieGoalUiModule
import com.dominikgold.calorietracker.ui.home.HomeScreenUiModule
import com.dominikgold.calorietracker.ui.settings.SettingsUiModule
import com.dominikgold.calorietracker.ui.topbar.TopBarUiModule
import com.dominikgold.calorietracker.usecases.UseCasesModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    UseCasesModule::class,

    NavigationModule::class,
    RepositoriesModule::class,
    DataSourcesModule::class,
    UtilsModule::class,

    SetCalorieGoalUiModule::class,
    HomeScreenUiModule::class,
    TopBarUiModule::class,
    BottomNavigationUiModule::class,
    SettingsUiModule::class,
    BodyWeightUiModule::class,
])
interface AppComponent {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(@ApplicationContext context: Context): Builder

    }

    fun inject(activity: MainActivity)

}