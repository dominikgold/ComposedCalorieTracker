package com.dominikgold.calorietracker.usecases.bodyweight

import com.dominikgold.calorietracker.entities.BodyWeightEntry
import com.dominikgold.calorietracker.entities.BodyWeightEntryPeriod
import com.dominikgold.calorietracker.entities.TimeInterval
import com.dominikgold.calorietracker.util.LocalDateProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.time.LocalDate

class DefaultGetBodyWeightUseCaseTest {

    @Mock
    private lateinit var repository: BodyWeightEntryRepository

    @Mock
    private lateinit var localDateProvider: LocalDateProvider

    @InjectMocks
    private lateinit var useCase: DefaultGetBodyWeightUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `body weight entry periods are loaded from correct date on for a weekly time interval`() = runBlockingTest {
        val localDate = LocalDate.of(2021, 3, 8)
        val timeInterval = TimeInterval.Weekly
        val amountOfPeriods = 6
        val expectedDate = LocalDate.of(2021, 1, 25)
        whenever(localDateProvider.getLocalDateForToday()).thenReturn(localDate)
        whenever(repository.getBodyWeightEntriesAfterDate(any())).thenReturn(listOf())

        useCase.getBodyWeightEntryPeriods(timeInterval, amountOfPeriods)

        verify(repository).getBodyWeightEntriesAfterDate(expectedDate)
    }

    @Test
    fun `body weight entry periods are loaded from correct date on for a monthly time interval`() = runBlockingTest {
        val localDate = LocalDate.of(2021, 3, 8)
        val timeInterval = TimeInterval.Monthly
        val amountOfPeriods = 6
        val expectedDate = LocalDate.of(2020, 9, 9)
        whenever(localDateProvider.getLocalDateForToday()).thenReturn(localDate)
        whenever(repository.getBodyWeightEntriesAfterDate(any())).thenReturn(listOf())

        useCase.getBodyWeightEntryPeriods(timeInterval, amountOfPeriods)

        verify(repository).getBodyWeightEntriesAfterDate(expectedDate)
    }

    @Test
    fun `loaded body weight entries are grouped correctly into weekly periods`() = runBlockingTest {
        val localDate = LocalDate.of(2021, 3, 8)
        val timeInterval = TimeInterval.Weekly
        val amountOfPeriods = 3
        whenever(localDateProvider.getLocalDateForToday()).thenReturn(localDate)
        whenever(repository.getBodyWeightEntriesAfterDate(any())).thenReturn(listOf(
            // first week before today
            BodyWeightEntry(date = LocalDate.of(2021, 3, 5), 90.0),
            BodyWeightEntry(date = LocalDate.of(2021, 3, 3), 90.0),
            // second week before today
            BodyWeightEntry(date = LocalDate.of(2021, 2, 28), 90.0),
            // third week before today
            BodyWeightEntry(date = LocalDate.of(2021, 2, 22), 90.0),
            BodyWeightEntry(date = LocalDate.of(2021, 2, 20), 90.0),
        ))

        val result = useCase.getBodyWeightEntryPeriods(timeInterval, amountOfPeriods)

        result shouldBeEqualTo listOf(
            BodyWeightEntryPeriod(
                timeInterval = TimeInterval.Weekly,
                bodyWeightEntries = listOf(
                    BodyWeightEntry(date = LocalDate.of(2021, 3, 5), 90.0),
                    BodyWeightEntry(date = LocalDate.of(2021, 3, 3), 90.0),
                ),
            ),
            BodyWeightEntryPeriod(
                timeInterval = TimeInterval.Weekly,
                bodyWeightEntries = listOf(BodyWeightEntry(date = LocalDate.of(2021, 2, 28), 90.0)),
            ),
            BodyWeightEntryPeriod(
                timeInterval = TimeInterval.Weekly,
                bodyWeightEntries = listOf(
                    BodyWeightEntry(date = LocalDate.of(2021, 2, 22), 90.0),
                    BodyWeightEntry(date = LocalDate.of(2021, 2, 20), 90.0),
                ),
            ),
        )
    }

    @Test
    fun `loaded body weight entries are grouped correctly into monthly periods`() = runBlockingTest {
        val localDate = LocalDate.of(2021, 3, 8)
        val timeInterval = TimeInterval.Monthly
        val amountOfPeriods = 3
        whenever(localDateProvider.getLocalDateForToday()).thenReturn(localDate)
        whenever(repository.getBodyWeightEntriesAfterDate(any())).thenReturn(listOf(
            // first month before today
            BodyWeightEntry(date = LocalDate.of(2021, 3, 5), 90.0),
            BodyWeightEntry(date = LocalDate.of(2021, 2, 7), 90.0),
            // second month before today
            BodyWeightEntry(date = LocalDate.of(2021, 2, 3), 90.0),
            // third month before today
            BodyWeightEntry(date = LocalDate.of(2021, 1, 4), 90.0),
            BodyWeightEntry(date = LocalDate.of(2020, 12, 20), 90.0),
        ))

        val result = useCase.getBodyWeightEntryPeriods(timeInterval, amountOfPeriods)

        result shouldBeEqualTo listOf(
            BodyWeightEntryPeriod(
                timeInterval = TimeInterval.Monthly,
                bodyWeightEntries = listOf(
                    BodyWeightEntry(date = LocalDate.of(2021, 3, 5), 90.0),
                    BodyWeightEntry(date = LocalDate.of(2021, 2, 7), 90.0),
                ),
            ),
            BodyWeightEntryPeriod(
                timeInterval = TimeInterval.Monthly,
                bodyWeightEntries = listOf(BodyWeightEntry(date = LocalDate.of(2021, 2, 3), 90.0)),
            ),
            BodyWeightEntryPeriod(
                timeInterval = TimeInterval.Monthly,
                bodyWeightEntries = listOf(
                    BodyWeightEntry(date = LocalDate.of(2021, 1, 4), 90.0),
                    BodyWeightEntry(date = LocalDate.of(2020, 12, 20), 90.0),
                ),
            ),
        )
    }

}