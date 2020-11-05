package com.dominikgold.calorietracker.datasources.db

import android.content.Context
import androidx.room.Room
import com.dominikgold.calorietracker.di.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomDbModule {

    @Provides
    @Singleton
    fun provideCalorieTrackerRoomDb(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, CalorieTrackerRoomDb::class.java, "calorie_tracker.db")
        .fallbackToDestructiveMigration()
        .build()

}