package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.usecases.caloriegoal.CalorieGoalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCalorieGoalRepository @Inject constructor(
    private val appProtoStore: AppProtoStore,
) : CalorieGoalRepository {

    override suspend fun saveCalorieGoal(calorieGoal: CalorieGoal) {
        appProtoStore.saveCalorieGoal(calorieGoal)
    }

    override suspend fun getCurrentCalorieGoal(): CalorieGoal? {
        return appProtoStore.getCalorieGoal()
    }

}