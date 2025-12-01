package dmitriy.losev.cs

import java.time.DayOfWeek
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Класс для определения расписания работы
 */
data class Schedule(
    val startTime: LocalTime,
    val endTime: LocalTime,
    val workDays: Set<DayOfWeek>,
    val timeZone: ZoneId = ZoneId.systemDefault()
) {
    fun isTimeAllowed(dateTime: ZonedDateTime): Boolean {
        val time = dateTime.toLocalTime()
        val dayOfWeek = dateTime.dayOfWeek

        return dayOfWeek in workDays && time >= startTime && time < endTime
    }

    companion object {

        val WEEKDAYS = setOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
        )

        val WEEKEND = setOf(
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY
        )

        val ALL_DAYS = DayOfWeek.entries.toSet()
    }
}
