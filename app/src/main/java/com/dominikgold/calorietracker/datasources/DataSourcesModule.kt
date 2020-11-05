package com.dominikgold.calorietracker.datasources

import com.dominikgold.calorietracker.datasources.db.RoomDbModule
import com.dominikgold.calorietracker.repositories.AppProtoStore
import dagger.Binds
import dagger.Module

@Module(includes = [RoomDbModule::class])
interface DataSourcesModule {

    @Binds
    fun bindDefaultAppProtoStore(protoStore: DefaultAppProtoStore): AppProtoStore

}