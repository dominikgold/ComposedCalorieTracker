package com.dominikgold.calorietracker.usecases.caloriegoal

import com.dominikgold.calorietracker.entities.CalorieGoal

interface CalorieGoalRepository {

    fun saveCalorieGoal(calorieGoal: CalorieGoal)

    suspend fun getCurrentCalorieGoal(): CalorieGoal?

}