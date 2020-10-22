package com.dominikgold.calorietracker.usecases.caloriegoal

import com.dominikgold.calorietracker.entities.CalorieGoal

interface CalorieGoalRepository {

    suspend fun saveCalorieGoal(calorieGoal: CalorieGoal)

    suspend fun getCurrentCalorieGoal(): CalorieGoal?

}