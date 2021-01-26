package com.dominikgold.calorietracker.ui.caloriegoal

import com.dominikgold.compose.viewmodel.ViewModel
import com.dominikgold.calorietracker.di.ViewModelFactory
import com.dominikgold.calorietracker.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SetCalorieGoalUiModule {

    @Binds
    @IntoMap
    @ViewModelKey(SetCalorieGoalViewModel::class)
    fun bindSetCalorieGoalViewModelFactory(factory: SetCalorieGoalViewModelFactory)
            : ViewModelFactory<ViewModel, Any, Any>

}