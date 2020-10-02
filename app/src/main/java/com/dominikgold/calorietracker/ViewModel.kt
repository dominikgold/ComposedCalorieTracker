package com.dominikgold.calorietracker

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.util.concurrent.CancellationException
import kotlin.reflect.KClass

open class ViewModel {

    protected val coroutineScope: CoroutineScope = ViewModelScope()

    fun release() {
        coroutineScope.cancel(CancellationException("ViewModel has been released"))
    }

    class ViewModelScope : CoroutineScope {

        override val coroutineContext = SupervisorJob() + Dispatchers.Main.immediate
    }

}

data class ViewModelTag(val viewModelClass: KClass<out ViewModel>, val parameters: Any?)
