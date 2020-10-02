package com.dominikgold.calorietracker

import android.app.Application
import com.dominikgold.calorietracker.di.AppComponent
import com.dominikgold.calorietracker.di.DaggerAppComponent

class CalorieTrackerApplication : Application() {

    lateinit var daggerAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        daggerAppComponent = DaggerAppComponent.builder()
                .application(this)
                .build()
    }
}