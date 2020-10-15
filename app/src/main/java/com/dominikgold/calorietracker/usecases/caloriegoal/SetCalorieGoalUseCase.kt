package com.dominikgold.calorietracker.usecases.caloriegoal

import com.dominikgold.calorietracker.entities.CalorieGoal
import javax.inject.Inject
import javax.inject.Singleton

interface SetCalorieGoalUseCase {

    fun setCalorieGoal(calorieGoal: CalorieGoal)

}

class DefaultSetCalorieGoalUseCase @Inject constructor(
    private val repository: CalorieGoalRepository,
) : SetCalorieGoalUseCase {

    override fun setCalorieGoal(calorieGoal: CalorieGoal) {
        repository.saveCalorieGoal(calorieGoal)
    }

}
