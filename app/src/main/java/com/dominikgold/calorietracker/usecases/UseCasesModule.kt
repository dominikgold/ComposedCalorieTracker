package com.dominikgold.calorietracker.usecases

import com.dominikgold.calorietracker.usecases.bodyweight.DefaultGetBodyWeightUseCase
import com.dominikgold.calorietracker.usecases.bodyweight.DefaultSaveBodyWeightUseCase
import com.dominikgold.calorietracker.usecases.bodyweight.GetBodyWeightUseCase
import com.dominikgold.calorietracker.usecases.bodyweight.SaveBodyWeightUseCase
import com.dominikgold.calorietracker.usecases.caloriegoal.DefaultGetCalorieGoalUseCase
import com.dominikgold.calorietracker.usecases.caloriegoal.DefaultSetCalorieGoalUseCase
import com.dominikgold.calorietracker.usecases.caloriegoal.GetCalorieGoalUseCase
import com.dominikgold.calorietracker.usecases.caloriegoal.SetCalorieGoalUseCase
import com.dominikgold.calorietracker.usecases.intakeentries.DefaultDeleteIntakeEntryUseCase
import com.dominikgold.calorietracker.usecases.intakeentries.DefaultGetIntakeEntriesUseCase
import com.dominikgold.calorietracker.usecases.intakeentries.DefaultSaveIntakeEntryUseCase
import com.dominikgold.calorietracker.usecases.intakeentries.DeleteIntakeEntryUseCase
import com.dominikgold.calorietracker.usecases.intakeentries.GetIntakeEntriesUseCase
import com.dominikgold.calorietracker.usecases.intakeentries.SaveIntakeEntryUseCase
import dagger.Binds
import dagger.Module

@Module
interface UseCasesModule {

    @Binds
    fun bindGetIntakeEntriesUseCase(useCase: DefaultGetIntakeEntriesUseCase): GetIntakeEntriesUseCase

    @Binds
    fun bindSaveIntakeEntryUseCase(useCase: DefaultSaveIntakeEntryUseCase): SaveIntakeEntryUseCase

    @Binds
    fun bindDeleteIntakeEntryUseCase(useCase: DefaultDeleteIntakeEntryUseCase): DeleteIntakeEntryUseCase

    @Binds
    fun bindSetCalorieGoalUseCase(useCase: DefaultSetCalorieGoalUseCase): SetCalorieGoalUseCase

    @Binds
    fun bindGetCalorieGoalUseCase(useCase: DefaultGetCalorieGoalUseCase): GetCalorieGoalUseCase

    @Binds
    fun bindSaveBodyWeightUseCase(useCase: DefaultSaveBodyWeightUseCase): SaveBodyWeightUseCase

    @Binds
    fun bindGetBodyWeightUseCase(useCase: DefaultGetBodyWeightUseCase): GetBodyWeightUseCase

}