package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.usecases.bodyweight.BodyWeightEntryRepository
import com.dominikgold.calorietracker.usecases.caloriegoal.CalorieGoalRepository
import com.dominikgold.calorietracker.usecases.intakeentries.IntakeEntryRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoriesModule {

    @Binds
    fun bindIntakeEntryRepository(repository: DefaultIntakeEntryRepository): IntakeEntryRepository

    @Binds
    fun bindCalorieGoalRepository(repository: DefaultCalorieGoalRepository): CalorieGoalRepository

    @Binds
    fun bindBodyWeightEntryRepository(repository: DefaultBodyWeightEntryRepository): BodyWeightEntryRepository

}