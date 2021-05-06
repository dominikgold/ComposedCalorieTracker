@file:JvmName("GetBodyWeightUseCase")

package com.dominikgold.calorietracker.usecases.bodyweight

import com.dominikgold.calorietracker.entities.BodyWeightEntry
import com.dominikgold.calorietracker.entities.BodyWeightEntryPeriod
import com.dominikgold.calorietracker.entities.TimeInterval
import com.dominikgold.calorietracker.util.LocalDateProvider
import javax.inject.Inject

interface GetBodyWeightUseCase {

    suspend fun getBodyWeightForToday(): Double?

    suspend fun getBodyWeightEntryPeriods(timeInterval: TimeInterval, amountOfPeriods: Int): List<BodyWeightEntryPeriod>

}

class DefaultGetBodyWeightUseCase @Inject constructor(
    private val repository: BodyWeightEntryRepository,
    private val localDateProvider: LocalDateProvider,
) : GetBodyWeightUseCase {

    override suspend fun getBodyWeightForToday(): Double? {
        return repository.getBodyWeightForToday()?.bodyWeight
    }

    override suspend fun getBodyWeightEntryPeriods(
        timeInterval: TimeInterval,
        amountOfPeriods: Int,
    ): List<BodyWeightEntryPeriod> {
        val numberOfDays = timeInterval.numberOfDays * amountOfPeriods
        val today = localDateProvider.getLocalDateForToday()
        val startDate = today.minusDays(numberOfDays.toLong())
        val bodyWeightEntries = repository.getBodyWeightEntriesAfterDate(after = startDate)
        return bodyWeightEntries.toMutableList().groupIntoPeriods(timeInterval, amountOfPeriods)
    }

    private fun MutableList<BodyWeightEntry>.groupIntoPeriods(
        timeInterval: TimeInterval,
        amountOfPeriods: Int,
    ): List<BodyWeightEntryPeriod> {
        val today = localDateProvider.getLocalDateForToday()
        val bodyWeightEntryPeriods = mutableListOf<BodyWeightEntryPeriod>()
        (1..amountOfPeriods).forEach { timePeriodIndex ->
            val afterDateForPeriod = today.minusDays((timePeriodIndex * timeInterval.numberOfDays).toLong())
            val beforeDateForPeriod = today.minusDays(((timePeriodIndex - 1) * timeInterval.numberOfDays).toLong() - 1)
            val bodyWeightEntriesForPeriod = mutableListOf<BodyWeightEntry>()
            val bodyWeightEntriesIterator = this.iterator()
            bodyWeightEntriesIterator.forEach {
                if (it.date.isAfter(afterDateForPeriod) && it.date.isBefore(beforeDateForPeriod)) {
                    bodyWeightEntriesForPeriod.add(it)
                    bodyWeightEntriesIterator.remove()
                }
            }
            bodyWeightEntryPeriods.add(BodyWeightEntryPeriod(bodyWeightEntriesForPeriod))
        }
        return bodyWeightEntryPeriods
    }

}