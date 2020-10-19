package com.dominikgold.calorietracker.di

import com.dominikgold.calorietracker.navigation.DefaultNavigator
import com.dominikgold.calorietracker.navigation.Navigator
import dagger.Binds
import dagger.Module

@Module
interface NavigationModule {

    @Binds
    fun bindNavigationStateContainer(navigationStateContainer: DefaultNavigator): Navigator

}