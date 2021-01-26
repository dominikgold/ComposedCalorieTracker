package com.dominikgold.calorietracker.di

import com.dominikgold.compose.viewmodel.ViewModel

interface ViewModelFactory<VM : ViewModel, SavedState, Parameters> {

    fun create(savedState: SavedState?, parameters: Parameters?): VM

}