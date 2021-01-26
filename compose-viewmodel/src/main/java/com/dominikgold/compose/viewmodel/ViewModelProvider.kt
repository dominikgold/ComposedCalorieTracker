package com.dominikgold.compose.viewmodel

import kotlin.reflect.KClass

interface ViewModelProvider {

    fun <VM : ViewModel, SavedState, Parameters> provide(
        viewModelClass: KClass<VM>,
        savedState: SavedState?,
        parameters: Parameters?,
    ): VM

}