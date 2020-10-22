package com.dominikgold.calorietracker.datasources

import com.dominikgold.calorietracker.repositories.AppProtoStore
import dagger.Binds
import dagger.Module

@Module
interface DataSourcesModule {

    @Binds
    fun bindDefaultAppProtoStore(protoStore: DefaultAppProtoStore): AppProtoStore

}