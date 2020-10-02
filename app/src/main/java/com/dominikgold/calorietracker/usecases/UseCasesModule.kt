package com.dominikgold.calorietracker.usecases

import com.dominikgold.calorietracker.usecases.caloriegoal.DefaultSetCalorieGoalUseCase
import com.dominikgold.calorietracker.usecases.caloriegoal.SetCalorieGoalUseCase
import com.dominikgold.calorietracker.usecases.intakeentries.DefaultLoadIntakeEntriesUseCase
import com.dominikgold.calorietracker.usecases.intakeentries.LoadIntakeEntriesUseCase
import dagger.Binds
import dagger.Module

@Module
interface UseCasesModule {

    @Binds
    fun bindLoadIntakeEntriesUseCase(useCase: DefaultLoadIntakeEntriesUseCase): LoadIntakeEntriesUseCase

    @Binds
    fun bindSetCalorieGoalUseCase(useCase: DefaultSetCalorieGoalUseCase): SetCalorieGoalUseCase

}