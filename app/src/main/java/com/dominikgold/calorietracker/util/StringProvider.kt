package com.dominikgold.calorietracker.util

import android.content.Context
import androidx.annotation.StringRes
import com.dominikgold.calorietracker.di.ApplicationContext
import javax.inject.Inject

interface StringProvider {

    fun getString(@StringRes stringResource: Int, vararg formatArgs: Any): String

}

class DefaultStringProvider @Inject constructor(@ApplicationContext private val applicationContext: Context) :
    StringProvider {

    override fun getString(@StringRes stringResource: Int, vararg formatArgs: Any): String {
        return applicationContext.getString(stringResource, formatArgs)
    }

}
