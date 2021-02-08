package com.dominikgold.calorietracker.ui.settings

import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.di.ViewModelKey
import com.dominikgold.compose.viewmodel.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SettingsUiModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModelFactory(factory: SettingsViewModelFactory): ViewModelFactory<ViewModel, Any, Any>

}