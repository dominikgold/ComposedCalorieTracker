package com.dominikgold.calorietracker.usecases.caloriegoal

import com.dominikgold.calorietracker.entities.CalorieGoal
import javax.inject.Inject

interface SetCalorieGoalUseCase {

    fun setCalorieGoal(calorieGoal: CalorieGoal)

}

class DefaultSetCalorieGoalUseCase @Inject constructor() : SetCalorieGoalUseCase {

    override fun setCalorieGoal(calorieGoal: CalorieGoal) {
        TODO("Not yet implemented")
    }

}
