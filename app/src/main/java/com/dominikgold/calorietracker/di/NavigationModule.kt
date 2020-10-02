package com.dominikgold.calorietracker.di

import com.dominikgold.calorietracker.navigation.DefaultNavigationStateContainer
import com.dominikgold.calorietracker.navigation.NavigationStateContainer
import dagger.Binds
import dagger.Module

@Module
interface NavigationModule {

    @Binds
    fun bindNavigationStateContainer(navigationStateContainer: DefaultNavigationStateContainer): NavigationStateContainer

}