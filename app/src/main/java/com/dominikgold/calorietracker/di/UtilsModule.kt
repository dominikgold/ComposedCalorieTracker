package com.dominikgold.calorietracker.di

import com.dominikgold.calorietracker.util.DefaultStringProvider
import com.dominikgold.calorietracker.util.StringProvider
import dagger.Binds
import dagger.Module

@Module
interface UtilsModule {

    @Binds
    fun bindStringProvider(defaultStringProvider: DefaultStringProvider): StringProvider

}