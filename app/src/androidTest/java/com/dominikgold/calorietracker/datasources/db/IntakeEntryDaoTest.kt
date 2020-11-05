package com.dominikgold.calorietracker.datasources.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class IntakeEntryDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: CalorieTrackerRoomDb

    private val testIntakeEntry = PersistedIntakeEntry(
        name = "lunch",
        calories = 100,
        carbohydrates = 10,
        protein = 10,
        fat = 10,
        date = LocalDate.now(),
    )

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), CalorieTrackerRoomDb::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getting_intake_entries_for_one_day() = runBlockingTest {
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry.copy(date = LocalDate.of(2020, 11, 4)))
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry.copy(date = LocalDate.of(2020, 11, 5)))
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry.copy(date = LocalDate.of(2020, 11, 5)))
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry.copy(date = LocalDate.of(2020, 11, 6)))

        val intakeEntries = db.intakeEntryDao().getIntakeEntriesForDate(LocalDate.of(2020, 11, 5))

        intakeEntries shouldBeEqualTo listOf(
            testIntakeEntry.copy(date = LocalDate.of(2020, 11, 5)),
            testIntakeEntry.copy(date = LocalDate.of(2020, 11, 5)),
        )
    }

    @Test
    fun getting_intake_entries_after_certain_day() = runBlockingTest {
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry.copy(date = LocalDate.of(2020, 11, 4)))
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry.copy(date = LocalDate.of(2019, 12, 1)))
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry.copy(date = LocalDate.of(2020, 11, 5)))
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry.copy(date = LocalDate.of(2020, 11, 5)))
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry.copy(date = LocalDate.of(2020, 11, 6)))
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry.copy(date = LocalDate.of(2020, 11, 20)))

        val intakeEntries = db.intakeEntryDao().getIntakeEntriesAfterDate(LocalDate.of(2020, 11, 5))

        intakeEntries shouldBeEqualTo listOf(
            testIntakeEntry.copy(date = LocalDate.of(2020, 11, 5)),
            testIntakeEntry.copy(date = LocalDate.of(2020, 11, 5)),
            testIntakeEntry.copy(date = LocalDate.of(2020, 11, 6)),
            testIntakeEntry.copy(date = LocalDate.of(2020, 11, 20)),
        )
    }

    @Test
    fun adding_intake_entries_auto_increments_ids() = runBlockingTest {
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry)
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry)
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry)
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry)

        val intakeEntries = db.intakeEntryDao().getIntakeEntriesForDate(testIntakeEntry.date)

        intakeEntries.forEachIndexed { index, persistedIntakeEntry ->
            persistedIntakeEntry.id shouldBeEqualTo index + 1
        }
    }

    @Test
    fun deleting_intake_entry_by_id() = runBlockingTest {
        db.intakeEntryDao().addIntakeEntry(testIntakeEntry)

        db.intakeEntryDao().deleteIntakeEntry(id = 1)
        val intakeEntries = db.intakeEntryDao().getIntakeEntriesForDate(testIntakeEntry.date)

        intakeEntries.shouldBeEmpty()
    }

}