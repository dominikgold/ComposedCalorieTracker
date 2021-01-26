package com.dominikgold.calorietracker.ui.bottomnav

import com.dominikgold.compose.viewmodel.ViewModel
import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface BottomNavigationUiModule {

    @Binds
    @IntoMap
    @ViewModelKey(BottomNavigationViewModel::class)
    fun bindBottomNavigationViewModelFactory(
        factory: BottomNavigationViewModelFactory,
    ): ViewModelFactory<ViewModel, Any, Any>

}