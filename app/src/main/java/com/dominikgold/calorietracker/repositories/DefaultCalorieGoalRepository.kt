package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.usecases.caloriegoal.CalorieGoalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCalorieGoalRepository @Inject constructor() : CalorieGoalRepository {

    private var currentCalorieGoal: CalorieGoal? = null

    override fun saveCalorieGoal(calorieGoal: CalorieGoal) {
        currentCalorieGoal = calorieGoal
    }

    override suspend fun getCurrentCalorieGoal(): CalorieGoal? {
        return currentCalorieGoal
    }

}