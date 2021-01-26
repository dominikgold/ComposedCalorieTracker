package com.dominikgold.compose.viewmodel

import kotlin.reflect.KClass

interface ViewModelContainer {

    fun <VM : ViewModel, SavedState, Parameters> get(
        viewModelClass: KClass<VM>,
        savedState: SavedState?,
        parameters: Parameters?,
    ): VM

    fun release()

}

class DefaultViewModelContainer(private val viewModelProvider: ViewModelProvider) : ViewModelContainer {

    private val viewModels = mutableMapOf<ViewModelTag, ViewModel>()

    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel, SavedState, Parameters> get(
        viewModelClass: KClass<VM>,
        savedState: SavedState?,
        parameters: Parameters?,
    ): VM {
        val tag = ViewModelTag(viewModelClass, parameters)
        val viewModel = viewModels.getOrPut(tag) { viewModelProvider.provide(viewModelClass, savedState, parameters) }
        return viewModel as VM
    }

    override fun release() {
        viewModels.values.forEach { it.release() }
        viewModels.clear()
    }

}
