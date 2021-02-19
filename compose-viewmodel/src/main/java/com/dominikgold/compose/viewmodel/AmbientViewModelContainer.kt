package com.dominikgold.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticAmbientOf

@Composable
inline fun <reified VM : ViewModel, SavedState, Parameters> viewModel(
    savedState: SavedState? = null,
    parameters: Parameters? = null,
) = AmbientViewModelContainer.current.get(VM::class, savedState, parameters)

@Composable
inline fun <reified VM : ViewModel, SavedState> viewModel(savedState: SavedState?) =
    AmbientViewModelContainer.current.get(VM::class, savedState, null)

@Composable
inline fun <reified VM : ViewModel> viewModel() = AmbientViewModelContainer.current.get(VM::class, null, null)

val AmbientViewModelContainer = staticAmbientOf<ViewModelContainer> {
    throw IllegalStateException("No ViewModelContainer provided")
}
