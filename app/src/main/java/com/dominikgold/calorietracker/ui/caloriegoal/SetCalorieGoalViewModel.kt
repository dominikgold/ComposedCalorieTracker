package com.dominikgold.calorietracker.ui.caloriegoal

import com.dominikgold.calorietracker.ViewModel
import com.dominikgold.calorietracker.di.ViewModelFactory
import javax.inject.Inject

class SetCalorieGoalViewModel : ViewModel() {

}

class SetCalorieGoalViewModelFactory @Inject constructor() :
    ViewModelFactory<SetCalorieGoalViewModel, Nothing, Nothing> {

    override fun create(savedState: Nothing?, parameters: Nothing?): SetCalorieGoalViewModel {
        return SetCalorieGoalViewModel()
    }

}
