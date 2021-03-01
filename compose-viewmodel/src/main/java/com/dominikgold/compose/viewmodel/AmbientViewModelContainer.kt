package com.dominikgold.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
inline fun <reified VM : ViewModel, SavedState, Parameters> viewModel(
    savedState: SavedState? = null,
    parameters: Parameters? = null,
) = LocalViewModelContainer.current.get(VM::class, savedState, parameters)

@Composable
inline fun <reified VM : ViewModel, SavedState> viewModel(savedState: SavedState?) =
    LocalViewModelContainer.current.get(VM::class, savedState, null)

@Composable
inline fun <reified VM : ViewModel> viewModel() = LocalViewModelContainer.current.get(VM::class, null, null)

val LocalViewModelContainer = staticCompositionLocalOf<ViewModelContainer> {
    throw IllegalStateException("No ViewModelContainer provided")
}
