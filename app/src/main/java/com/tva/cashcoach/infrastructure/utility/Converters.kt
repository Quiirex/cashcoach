package com.tva.cashcoach.infrastructure.utility

import androidx.room.TypeConverter
import java.util.Date

/**
 * Converters for Room database
 */
class Converters {

    /**
     * Convert from timestamp to date
     * @param value
     * @return Date
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    /**
     * Convert from date to timestamp
     * @param date
     * @return Long
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}
