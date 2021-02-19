package com.dominikgold.calorietracker.ui.bodyweight

import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.di.ViewModelKey
import com.dominikgold.compose.viewmodel.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface BodyWeightUiModule {

    @Binds
    @IntoMap
    @ViewModelKey(BodyWeightViewModel::class)
    fun bindBodyWeightViewModelFactory(viewModel: BodyWeightViewModelFactory): ViewModelFactory<ViewModel, Any, Any>

}