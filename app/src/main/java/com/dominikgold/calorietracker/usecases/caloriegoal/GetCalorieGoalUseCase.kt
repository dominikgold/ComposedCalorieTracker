package com.dominikgold.calorietracker.usecases.caloriegoal

import com.dominikgold.calorietracker.entities.CalorieGoal
import javax.inject.Inject

interface GetCalorieGoalUseCase {

    suspend fun getCalorieGoal(): CalorieGoal?

}

class DefaultGetCalorieGoalUseCase @Inject constructor(
    private val calorieGoalRepository: CalorieGoalRepository,
) : GetCalorieGoalUseCase {

    override suspend fun getCalorieGoal() = calorieGoalRepository.getCurrentCalorieGoal()
}
