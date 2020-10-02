package com.dominikgold.calorietracker.repositories

import com.dominikgold.calorietracker.usecases.intakeentries.IntakeEntryRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoriesModule {

    @Binds
    fun bindChoreRepository(repository: DefaultIntakeEntryRepository): IntakeEntryRepository

}