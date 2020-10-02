package com.dominikgold.calorietracker.di

import com.dominikgold.calorietracker.ViewModel

interface ViewModelFactory<VM : ViewModel, SavedState, Parameters> {

    fun create(savedState: SavedState?, parameters: Parameters?): VM

}