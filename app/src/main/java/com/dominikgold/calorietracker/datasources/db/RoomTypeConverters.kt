package com.dominikgold.calorietracker.datasources.db

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object RoomTypeConverters {

    private val localDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    @JvmStatic
    fun localDateToString(localDate: LocalDate?): String? = localDate?.format(localDateFormatter)

    @TypeConverter
    @JvmStatic
    fun localDateFromString(formattedLocalDate: String?): LocalDate? = formattedLocalDate?.let {
        localDateFormatter.parse(it, LocalDate::from)
    }

}