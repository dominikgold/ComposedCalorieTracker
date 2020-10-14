package com.dominikgold.calorietracker.ui.topbar

import com.dominikgold.calorietracker.ViewModel
import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TopBarUiModule {

    @Binds
    @IntoMap
    @ViewModelKey(TopBarViewModel::class)
    fun bindTopBarViewModelFactory(factory: TopBarViewModelFactory): ViewModelFactory<ViewModel, Any, Any>

}