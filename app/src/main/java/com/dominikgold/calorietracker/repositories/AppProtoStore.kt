package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.entities.CalorieGoal

interface AppProtoStore {

    suspend fun getCalorieGoal() : CalorieGoal?

    suspend fun saveCalorieGoal(calorieGoal: CalorieGoal)

}