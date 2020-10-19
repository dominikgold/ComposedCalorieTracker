package com.dominikgold.calorietracker.di

import com.dominikgold.calorietracker.ViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelProvider @Inject constructor(
    private val factories: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModelFactory<ViewModel, Any, Any>>>,
) {

    fun <VM : ViewModel, SavedState, Parameters> provide(
        viewModelClass: KClass<VM>,
        savedState: SavedState?,
        parameters: Parameters?,
    ): VM {
        val factory = factories[viewModelClass.java]
        @Suppress("UNCHECKED_CAST") return (factory?.get()?.create(savedState, parameters) as? VM)
            ?: throw UnknownViewModelTypeException(viewModelClass)
    }

}

class UnknownViewModelTypeException(type: KClass<out ViewModel>) :
    Exception("Encountered unknown ViewModel type: ${type.qualifiedName}")
