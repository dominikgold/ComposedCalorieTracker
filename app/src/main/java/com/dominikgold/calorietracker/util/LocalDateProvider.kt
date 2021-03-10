package com.dominikgold.calorietracker.util

import java.time.LocalDate
import javax.inject.Inject

interface LocalDateProvider {

    fun getLocalDateForToday(): LocalDate

}

class DefaultLocalDateProvider @Inject constructor(): LocalDateProvider {

    override fun getLocalDateForToday(): LocalDate = LocalDate.now()

}